package com.example.springproject.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.example.springproject.entity.Permission.*;

/**
 * Enumeration representing different roles in the system.
 * Each role has a corresponding set of permissions associated with it.
 * Provides a method to retrieve authorities for Spring Security.
 */
@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_CREATE,
            ADMIN_DELETE,
            ADMIN_UPDATE,
            MANAGER_CREATE,
            MANAGER_UPDATE,
            MANAGER_DELETE,
            MANAGER_READ
    )),
    MANAGER(Set.of(
            MANAGER_DELETE,
            MANAGER_CREATE,
            MANAGER_UPDATE,
            MANAGER_READ
    ))


    ;

    @Getter
    private final Set<Permission> permissionSet;


    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = new java.util.ArrayList<>(getPermissionSet()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}