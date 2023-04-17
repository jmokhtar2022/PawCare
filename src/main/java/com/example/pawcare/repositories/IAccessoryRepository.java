package com.example.pawcare.repositories;

import com.example.pawcare.entities.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccessoryRepository extends JpaRepository<Accessory,Long> {
}
