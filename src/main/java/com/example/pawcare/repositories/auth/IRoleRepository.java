package com.example.pawcare.repositories.auth;

import com.example.pawcare.entities.Role;
import com.example.pawcare.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
}
