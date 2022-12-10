package com.raf.usermanagementbackend.service;

import com.raf.usermanagementbackend.model.Role;
import com.raf.usermanagementbackend.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
}
