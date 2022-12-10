package com.raf.usermanagementbackend.utils;

import com.raf.usermanagementbackend.model.Role;
import com.raf.usermanagementbackend.model.RoleEnum;
import com.raf.usermanagementbackend.model.User;
import com.raf.usermanagementbackend.repository.RoleRepository;
import com.raf.usermanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BootstrapData implements CommandLineRunner {


    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public BootstrapData(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createUsers();
    }

    private void createRoles(){
        for(RoleEnum roleEnum: RoleEnum.values()){
            Role role = new Role();
            role.setRole(roleEnum);

            roleRepository.save(role);
        }
    }

    private void createUsers(){
        User userAdmin = new User();
        userAdmin.setName("Admin");
        userAdmin.setSurname("Admin");
        userAdmin.setEmail("admin@raf.rs");
        userAdmin.setPassword(passwordEncoder.encode("admin123"));
        userAdmin.setRoles(roleRepository.findAll());

        userRepository.save(userAdmin);

        User userRead = new User();
        userRead.setName("Read");
        userRead.setSurname("Read");
        userRead.setEmail("read@raf.rs");
        userRead.setPassword(passwordEncoder.encode("read123"));
        roleRepository.findByRole(RoleEnum.CAN_READ_USERS).ifPresent(roleRead -> userRead.setRoles(List.of(roleRead)));

        userRepository.save(userRead);

        User userCreate = new User();
        userCreate.setName("Create");
        userCreate.setSurname("Create");
        userCreate.setEmail("create@raf.rs");
        userCreate.setPassword(passwordEncoder.encode("create123"));
        roleRepository.findByRole(RoleEnum.CAN_CREATE_USERS).ifPresent(roleCreate -> userCreate.setRoles(List.of(roleCreate)));

        userRepository.save(userCreate);
    }

    private void deleteAll(){
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }
}
