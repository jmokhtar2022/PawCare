package com.example.pawcare.repositories;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Item;
import com.example.pawcare.entities.OrderStatus;
import com.example.pawcare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IItemRepository extends JpaRepository<Item,Long> {

    @Query("SELECT a FROM Item o JOIN o.cart c JOIN c.accessories a WHERE o.idItem = :idOrder")
    List<Accessory> findAccessoriesByOrderId(@Param("idOrder") Long orderId);

    Item findByIdItem(Long idOrder);
    List<Item> findByOrderstatusIn(List<OrderStatus> orderstatus);
    @Query("SELECT o.idItem, o.totalItem,o.orderstatus,o.date,  u.email FROM Item o JOIN o.user u WHERE o.idItem = :id")
    Item findOrderWithUserEmailById(@Param("id") Long id);

}
