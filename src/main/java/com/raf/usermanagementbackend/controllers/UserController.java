package com.raf.usermanagementbackend.controllers;

import com.raf.usermanagementbackend.dto.MessageDto;
import com.raf.usermanagementbackend.dto.user.UserCreateDto;
import com.raf.usermanagementbackend.dto.user.UserDto;
import com.raf.usermanagementbackend.dto.user.UserUpdateDto;
import com.raf.usermanagementbackend.model.User;
import com.raf.usermanagementbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDto userCreateDto){
        userService.createNewUser(userCreateDto);
        return ResponseEntity.ok(new MessageDto("User created successfully."));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto){
        userService.updateUser(userUpdateDto);
        return ResponseEntity.ok(new MessageDto("User updated successfully."));
    }
}
