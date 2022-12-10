package com.raf.usermanagementbackend.service;

import com.raf.usermanagementbackend.dto.user.UserCreateDto;
import com.raf.usermanagementbackend.dto.user.UserDto;
import com.raf.usermanagementbackend.dto.user.UserUpdateDto;
import com.raf.usermanagementbackend.mapper.UserMapper;
import com.raf.usermanagementbackend.model.Role;
import com.raf.usermanagementbackend.model.User;
import com.raf.usermanagementbackend.repository.RoleRepository;
import com.raf.usermanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User currentUser = this.userRepository.findByEmail(email);
        if(currentUser == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }

        //TODO PROSLEDI PRIVILEGIJE
        return new org.springframework.security.core.userdetails.User(currentUser.getEmail(), currentUser.getPassword(), new ArrayList<>());
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream().map(userMapper :: userToUserDto).collect(Collectors.toList());
    }

    public UserDto getUserById(Long userId){
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        return userMapper.userToUserDto(user);
    }

    public boolean deleteUserById(Long userId){
        if (userRepository.existsById(userId)){
            userRepository.deleteByUserId(userId);
            return true;
        }
        return false;
    }

    @Transactional
    public void createNewUser(UserCreateDto userCreateDto){
        User newUser = userMapper.userCreateDtoToUser(userCreateDto);
        roleRepository.findRoleByRoleIds(userCreateDto.getRoleIds()).ifPresent(newUser::setRoles);
        userRepository.save(newUser);
    }

    @Transactional
    public void updateUser(UserUpdateDto userUpdateDto){
        User user = userRepository.findByUserId(userUpdateDto.getUserId()).orElseThrow(EntityNotFoundException::new);
        userMapper.updateUser(user, userUpdateDto);
        roleRepository.findRoleByRoleIds(userUpdateDto.getRoleIds()).ifPresent(user::setRoles);
    }
}
