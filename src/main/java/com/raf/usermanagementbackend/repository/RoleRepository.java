package com.raf.usermanagementbackend.repository;

import com.raf.usermanagementbackend.model.Role;
import com.raf.usermanagementbackend.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(RoleEnum roleEnum);
}
