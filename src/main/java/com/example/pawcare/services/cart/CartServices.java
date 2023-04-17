package com.example.pawcare.services.cart;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Cart;

import java.util.List;

public interface CartServices {
    public List<Cart> retrieveAllCarts();

    //public Cart addCart(Cart cart);
    public Cart addCart(Cart cart, Long userId) ;

    public Cart updateCart(Cart cart, Long idCart);

    public Cart retrieveCartById(Long idCart);

    public void deleteCart(Long idCart);
    public Cart addAccessoryToCart(Long idAccessory,Long idCart);
    public Cart removeAccessoryFromCart(Long idAccessory,Long idCart);
    public Cart displayCartByUserId(Long id);

}
