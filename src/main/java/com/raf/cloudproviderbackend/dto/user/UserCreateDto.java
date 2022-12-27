package com.raf.cloudproviderbackend.dto.user;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UserCreateDto {

    @Email
    @NotNull @NotBlank @NotEmpty
    private String email;
    @Length(min = 4, max = 20)
    @NotNull @NotBlank @NotEmpty
    private String password;
    @NotNull @NotBlank @NotEmpty
    private String name;
    @NotNull @NotBlank @NotEmpty
    private String surname;
    private List<Long> roleIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
