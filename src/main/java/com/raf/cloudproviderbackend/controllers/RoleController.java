package com.raf.cloudproviderbackend.controllers;

import com.raf.cloudproviderbackend.model.user.RoleEnum;
import com.raf.cloudproviderbackend.security.CheckRole;
import com.raf.cloudproviderbackend.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
}
