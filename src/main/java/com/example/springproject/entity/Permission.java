package com.example.springproject.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration representing different permissions in the system.
 * Each permission is associated with a specific action or resource.
 */
@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_CREATE("admin:create"),
    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_DELETE("manager:delete"),
    MANAGER_CREATE("manager:create")
    ;

    @Getter
    private final String permission;
}