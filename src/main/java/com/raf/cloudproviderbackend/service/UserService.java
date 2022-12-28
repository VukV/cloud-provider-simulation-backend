package com.raf.cloudproviderbackend.service;

import com.raf.cloudproviderbackend.dto.user.UserCreateDto;
import com.raf.cloudproviderbackend.dto.user.UserDto;
import com.raf.cloudproviderbackend.dto.user.UserUpdateDto;
import com.raf.cloudproviderbackend.exceptions.UserNotFoundException;
import com.raf.cloudproviderbackend.mapper.UserMapper;
import com.raf.cloudproviderbackend.model.user.User;
import com.raf.cloudproviderbackend.repository.RoleRepository;
import com.raf.cloudproviderbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User currentUser = this.userRepository.findByEmail(email);
        if(currentUser == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }

        return new org.springframework.security.core.userdetails.User(currentUser.getEmail(), currentUser.getPassword(), currentUser.getRoles());
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAllUsers().stream().map(userMapper :: userToUserDto).collect(Collectors.toList());
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public UserDto getUserById(Long userId){
        User user = userRepository.findByUserId(userId).orElse(null);
        if(user != null){
            return userMapper.userToUserDto(user);
        }
        throw new UserNotFoundException();
    }

    @Transactional
    public void deleteUserById(Long userId){
        if (userRepository.existsById(userId)){
            userRepository.deleteByUserId(userId);
        }
        throw new UserNotFoundException();
    }

    @Transactional
    public void createNewUser(UserCreateDto userCreateDto){
        User newUser = userMapper.userCreateDtoToUser(userCreateDto);
        roleRepository.findRoleByRoleIds(userCreateDto.getRoleIds()).ifPresent(newUser::setRoles);
        userRepository.save(newUser);
    }

    @Transactional
    public void updateUser(UserUpdateDto userUpdateDto){
        User user = userRepository.findByUserId(userUpdateDto.getUserId()).orElseThrow(UserNotFoundException::new);
        userMapper.updateUser(user, userUpdateDto);
        roleRepository.findRoleByRoleIds(userUpdateDto.getRoleIds()).ifPresent(user::setRoles);
    }
}
