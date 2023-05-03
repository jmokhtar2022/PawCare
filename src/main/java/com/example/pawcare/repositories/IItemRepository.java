package com.example.pawcare.repositories;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Item;
import com.example.pawcare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IItemRepository extends JpaRepository<Item,Long> {

    @Query("SELECT a FROM Item o JOIN o.cart c JOIN c.accessories a WHERE o.idItem = :idOrder")
    List<Accessory> findAccessoriesByOrderId(@Param("idOrder") Long orderId);

    Item findByIdItem(Long idOrder);

}
