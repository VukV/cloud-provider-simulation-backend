package com.raf.usermanagementbackend.controllers;

import com.raf.usermanagementbackend.model.Role;
import com.raf.usermanagementbackend.model.RoleEnum;
import com.raf.usermanagementbackend.security.CheckRole;
import com.raf.usermanagementbackend.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @CheckRole(roles = RoleEnum.CREATE)
    public ResponseEntity<?> getAllRoles(){
        return new ResponseEntity<List<Role>>(roleService.getAllRoles(), HttpStatus.OK);
    }
}
