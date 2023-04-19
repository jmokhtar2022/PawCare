package com.example.pawcare.repositories.auth;

import com.example.pawcare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("SELECT u.username, u.email, r.name FROM User u JOIN u.roles r")
    List<Object[]> GetAllUsers();

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
