package com.raf.usermanagementbackend.controllers;

import com.raf.usermanagementbackend.dto.MessageDto;
import com.raf.usermanagementbackend.dto.user.UserCreateDto;
import com.raf.usermanagementbackend.dto.user.UserDto;
import com.raf.usermanagementbackend.dto.user.UserUpdateDto;
import com.raf.usermanagementbackend.model.RoleEnum;
import com.raf.usermanagementbackend.security.CheckRole;
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
    @CheckRole(roles = RoleEnum.CAN_READ_USERS)
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    @CheckRole(roles = {RoleEnum.CAN_CREATE_USERS, RoleEnum.CAN_UPDATE_USERS, RoleEnum.CAN_DELETE_USERS})
    public UserDto getUserById(@PathVariable("userId") Long userId){
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    @CheckRole(roles = RoleEnum.CAN_DELETE_USERS)
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId){
        if(userService.deleteUserById(userId)){
            return ResponseEntity.ok(new MessageDto("User deleted successfully."));
        }
        return ResponseEntity.status(404).body(new MessageDto("User not found."));
    }

    @PostMapping
    @CheckRole(roles = RoleEnum.CAN_CREATE_USERS)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDto userCreateDto){
        userService.createNewUser(userCreateDto);
        return ResponseEntity.ok(new MessageDto("User created successfully."));
    }

    @PutMapping
    @CheckRole(roles = RoleEnum.CAN_UPDATE_USERS)
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto){
        userService.updateUser(userUpdateDto);
        return ResponseEntity.ok(new MessageDto("User updated successfully."));
    }
}
