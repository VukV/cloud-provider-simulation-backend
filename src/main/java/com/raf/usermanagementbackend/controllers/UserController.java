package com.raf.usermanagementbackend.controllers;

import com.raf.usermanagementbackend.dto.MessageDto;
import com.raf.usermanagementbackend.dto.user.UserCreateDto;
import com.raf.usermanagementbackend.dto.user.UserDto;
import com.raf.usermanagementbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId){
        if(userService.deleteUserById(userId)){
            return ResponseEntity.ok(new MessageDto("User deleted successfully."));
        }
        return ResponseEntity.status(404).body(new MessageDto("User not found."));
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserCreateDto userCreateDto){
        //todo
        return null;
    }

    public ResponseEntity<?> updateUser(){
        //todo
        return null;
    }
}
