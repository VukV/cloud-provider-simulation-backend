package com.raf.usermanagementbackend.repository;

import com.raf.usermanagementbackend.model.Role;
import com.raf.usermanagementbackend.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(RoleEnum roleEnum);

    @Query("select r from Role where roleId in :roleIds")
    Optional<List<Role>> findRoleByRoleIds(@Param("roleIds")List<Long> roleIds);
}
