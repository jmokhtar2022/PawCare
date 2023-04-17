package com.example.pawcare.repositories;

import com.example.pawcare.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemRepository extends JpaRepository<Item,Long> {
}
