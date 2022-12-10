package com.raf.usermanagementbackend.mapper;

import com.raf.usermanagementbackend.dto.UserDto;
import com.raf.usermanagementbackend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();

        userDto.setUserId(user.getUserId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}
