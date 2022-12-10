package com.raf.usermanagementbackend.service;

import com.raf.usermanagementbackend.dto.UserDto;
import com.raf.usermanagementbackend.mapper.UserMapper;
import com.raf.usermanagementbackend.model.User;
import com.raf.usermanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
}
