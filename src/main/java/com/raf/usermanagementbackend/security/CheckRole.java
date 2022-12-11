package com.raf.usermanagementbackend.security;

import com.raf.usermanagementbackend.model.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckRole {

    RoleEnum[] roles() default {};

}
