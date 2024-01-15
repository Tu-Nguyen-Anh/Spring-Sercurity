package com.example.springproject.repository;

import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for managing User entities. Extends the BaseRepository interface.
 */
public interface UserRepository extends BaseRepository<User>{

    Optional<User> findUserByUsername(String username);
    @Query("""
              select new com.example.springproject.dto.response.UserResponse
              (u.id, u.username,u.email,u.phone,u.role,u.dateOfBirth)
              from User u
          """)
    Page<UserResponse> findAllUser(Pageable pageable);
}
