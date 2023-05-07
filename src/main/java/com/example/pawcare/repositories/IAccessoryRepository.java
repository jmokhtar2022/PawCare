package com.example.pawcare.repositories;
import com.example.pawcare.entities.Accessory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccessoryRepository extends JpaRepository<Accessory,Long> {
    Page<Accessory> findAll(Pageable pageable);
    List<Accessory> findByNameContainingIgnoreCase(String name);
    List<Accessory> findByPrice(Float price);
    List<Accessory> findByNameContainingIgnoreCaseAndPrice(String name, float price);

}


