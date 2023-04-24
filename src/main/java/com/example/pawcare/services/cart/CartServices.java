package com.example.pawcare.services.cart;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Cart;
import com.example.pawcare.entities.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CartServices {

    public Cart assignCart(Cart cart, Long userId) ;
    public Cart GetCartById(Long idCart);
    public Cart addCart(Cart cart);
    public Cart addAccessoryToCart(Long idAccessory,Long idCart);
    public Cart removeAccessoryFromCart(Long idAccessory,Long idCart);
    public Cart displayCartByUserId(Long id);
    public float getTotalCartPrice(Long cartId) ;
    public void emptyCart( Long cartId) ;
    public float calculateTotalCartPrice(Long cartId);
    public ResponseEntity<String> confirmOrder(@RequestBody Item order) ;
    public Cart updateCart(Cart cart, Long idCart);


}
