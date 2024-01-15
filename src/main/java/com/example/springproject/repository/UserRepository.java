package com.example.springproject.repository;

import com.example.springproject.entity.User;

import java.util.Optional;

/**
 * Repository interface for managing User entities. Extends the BaseRepository interface.
 */
public interface UserRepository extends BaseRepository<User>{

    Optional<User> findUserByUsername(String username);
}
