package com.raf.usermanagementbackend.service;

import com.raf.usermanagementbackend.model.User;
import com.raf.usermanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User currentUser = this.userRepository.findByUsername(username);
        if(currentUser == null) {
            throw new UsernameNotFoundException("User name "+username+" not found");
        }

        //TODO PROVERA ZA MEJL I PRIVILEGIJE
        return new org.springframework.security.core.userdetails.User(currentUser.getEmail(), currentUser.getPassword(), new ArrayList<>());
    }
}
