package com.example.pawcare.repositories;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
    User findUserById(Long id);

}
