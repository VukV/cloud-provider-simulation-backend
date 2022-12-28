package com.raf.cloudproviderbackend.mapper;

import com.raf.cloudproviderbackend.dto.user.UserCreateDto;
import com.raf.cloudproviderbackend.dto.user.UserDto;
import com.raf.cloudproviderbackend.dto.user.UserUpdateDto;
import com.raf.cloudproviderbackend.model.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();

        userDto.setUserId(user.getUserId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto){
        User user = new User();

        user.setEmail(userCreateDto.getEmail());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setName(userCreateDto.getName());
        user.setSurname(userCreateDto.getSurname());

        return user;
    }

    public void updateUser(User user, UserUpdateDto userUpdateDto){
        user.setName(userUpdateDto.getName());
        user.setSurname(userUpdateDto.getSurname());
        user.setEmail(userUpdateDto.getEmail());
    }
}
