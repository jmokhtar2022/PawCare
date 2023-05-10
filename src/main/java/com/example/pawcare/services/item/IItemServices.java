package com.example.pawcare.services.item;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Item;
import com.example.pawcare.entities.User;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IItemServices {

    public List<Item> retrieveAllItems();

    public Item addItem(Item item);

    public Item updateItem(Item item, Long idItem);

    public Item retrieveItemById(Long idItem);

   // public byte[] generateBillPdf(Item item) throws IOException;

    //public ResponseEntity<Item> createOrderFromCart(Item order, Long idCart, Long idUser) ;
    public void deleteItem(long idItem);

    public ResponseEntity<Item> createOrderFromCart(Long idCart, Long userId);

    public Map<String, Object> payOrder(Long orderId,
                                        Long userId,
                                        String cardNumber,
                                        String expMonth,
                                        String expYear,
                                        String cvc) throws StripeException;

    public void updateOrderStatusAsPaid(Long orderId);
    public List<Accessory> getAccessoriesByOrderId(Long orderId) ;
    public User getUserByIdOrder(Long idOrder) ;
    public byte[] generateBillPdf(Long itemId) throws IOException ;


    }