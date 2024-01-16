package com.example.springproject.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration representing different permissions in the system.
 * Each permission is associated with a specific action or resource.
 */
@RequiredArgsConstructor
public enum Permission {

    VIEW_ALL_USERS,
    CREATE_USER,
    UPDATE_USER,
    VIEW_USER_DETAILS,
    ;

}