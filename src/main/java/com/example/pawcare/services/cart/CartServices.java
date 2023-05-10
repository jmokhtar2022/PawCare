package com.example.pawcare.services.cart;

import com.example.pawcare.entities.Cart;
import com.example.pawcare.entities.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface CartServices {

    //public Cart assignCart(Cart cart, Long userId) ;
    public Cart GetCartById(Long idCart);
    public Cart addCart(Cart cart);
    public Cart createCart() ;
    public Cart addAccessoryToCart(Long idAccessory,Long idCart);
    public Cart removeAccessoryFromCart(Long idAccessory,Long idCart);
    public Cart displayCartByCartId(Long idCart);
    public float getTotalCartPrice(Long idCart) ;
    public void emptyCart( Long cartId) ;
    public float calculateTotalCartPrice(Long idCart);
    public ResponseEntity<String> confirmOrder(@RequestBody Item order) ;
    public Cart updateCart(Cart cart, Long idCart);
    public void deleteCart(Long idCart) ;


}
