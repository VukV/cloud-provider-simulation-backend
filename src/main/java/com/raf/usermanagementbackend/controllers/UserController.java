package com.raf.usermanagementbackend.controllers;

import com.raf.usermanagementbackend.dto.MessageDto;
import com.raf.usermanagementbackend.dto.user.UserCreateDto;
import com.raf.usermanagementbackend.dto.user.UserDto;
import com.raf.usermanagementbackend.dto.user.UserUpdateDto;
import com.raf.usermanagementbackend.model.RoleEnum;
import com.raf.usermanagementbackend.security.CheckRole;
import com.raf.usermanagementbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @CheckRole(roles = {RoleEnum.READ, RoleEnum.DELETE, RoleEnum.UPDATE})
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @CheckRole(roles = {RoleEnum.CREATE, RoleEnum.UPDATE, RoleEnum.DELETE})
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId){
        UserDto userDto = userService.getUserById(userId);
        if(userDto != null){
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        else {
            return ResponseEntity.status(404).body(new MessageDto("User not found."));
        }
    }

    @DeleteMapping("/{userId}")
    @CheckRole(roles = RoleEnum.DELETE)
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId){
        if(userService.deleteUserById(userId)){
            return ResponseEntity.ok(new MessageDto("User deleted successfully."));
        }
        return ResponseEntity.status(404).body(new MessageDto("User not found."));
    }

    @PostMapping
    @CheckRole(roles = RoleEnum.CREATE)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDto userCreateDto){
        userService.createNewUser(userCreateDto);
        return ResponseEntity.ok(new MessageDto("User created successfully."));
    }

    @PutMapping
    @CheckRole(roles = RoleEnum.UPDATE)
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto){
        userService.updateUser(userUpdateDto);
        return ResponseEntity.ok(new MessageDto("User updated successfully."));
    }
}
