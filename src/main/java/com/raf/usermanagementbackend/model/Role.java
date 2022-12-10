package com.raf.usermanagementbackend.model;

import javax.persistence.*;

@Entity
@Table(name = "roles", indexes = { @Index(columnList = "role")})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated (EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleEnum role;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
