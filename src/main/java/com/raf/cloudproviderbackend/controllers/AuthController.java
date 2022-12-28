package com.raf.cloudproviderbackend.controllers;

import com.raf.cloudproviderbackend.dto.MessageDto;
import com.raf.cloudproviderbackend.dto.login.LoginRequestDto;
import com.raf.cloudproviderbackend.dto.login.LoginResponseDto;
import com.raf.cloudproviderbackend.model.user.Role;
import com.raf.cloudproviderbackend.model.user.User;
import com.raf.cloudproviderbackend.service.UserService;
import com.raf.cloudproviderbackend.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        } catch (Exception   e){
            e.printStackTrace();
            return ResponseEntity.status(401).body(new MessageDto("Invalid login parameters."));
        }

        User user = userService.getUserByEmail(loginRequestDto.getEmail());
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("roles", user.getRoles().stream().map(Role::getRole).collect(Collectors.toList()));
        return ResponseEntity.ok(new LoginResponseDto(jwtUtil.generateToken(loginRequestDto.getEmail(), claims)));
    }

}
