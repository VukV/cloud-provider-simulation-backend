package com.raf.usermanagementbackend.security;

import com.raf.usermanagementbackend.dto.MessageDto;
import com.raf.usermanagementbackend.model.Role;
import com.raf.usermanagementbackend.model.RoleEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Configuration
public class CheckRoleAspect {

    @Around("@annotation(com.raf.usermanagementbackend.security.CheckRole)")
    public Object aroundCheckRole(ProceedingJoinPoint joinPoint) throws Throwable {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();

            List<GrantedAuthority> userRoles = new ArrayList<>(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            List<RoleEnum> roles = List.of(method.getAnnotation(CheckRole.class).roles());

            if(roles.isEmpty()){
                return joinPoint.proceed();
            }

            if(userRoles.isEmpty()){
                return ResponseEntity.status(403).body(new MessageDto("User unauthorized."));
            }
            else {
                for(GrantedAuthority role: userRoles){
                    if (role instanceof Role){
                        if(roles.contains(((Role) role).getRole())){
                            return joinPoint.proceed();
                        }
                    }
                }
            }

            return ResponseEntity.status(403).body(new MessageDto("User unauthorized."));
        }
        else {
            return ResponseEntity.status(401).body(new MessageDto("User not authenticated."));
        }
    }
}
