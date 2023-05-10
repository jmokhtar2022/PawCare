package com.example.pawcare.controllers;

import com.example.pawcare.services.email.MailService;
import com.example.pawcare.entities.*;
import com.example.pawcare.services.cart.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("cart")
public class CartController {
    @Autowired
    CartServices cartServices;
    @Autowired
    MailService mailService;



    @PostMapping("/createCart")
    public ResponseEntity<Cart> createCart() {
        Cart cart = cartServices.createCart();
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }


  /*  @PostMapping("/addCart/{id}") //id for idUser
    public Cart addCart(@RequestBody Cart cart, @PathVariable("id") Long id) {
        return cartServices.assignCart(cart, id);

    }

    @GetMapping("/displayCartByUserId/{id}")
    public Cart displayCartByUserId(@PathVariable("id") Long id) //id for idUser
    {
        return cartServices.displayCartByUserId(id);
    }*/

    @GetMapping("/getCartById/{idCart}")
    public Cart GetCartById(@PathVariable("idCart") Long idCart) {
        return cartServices.GetCartById(idCart);
    }

    @PutMapping("/addAccessoryToCart/{idAccessory}/{idCart}")
    public Cart addAccessoryToCart(@PathVariable("idAccessory") Long idAccessory, @PathVariable("idCart") Long idCart) {
        return cartServices.addAccessoryToCart(idAccessory, idCart);
    }

    @DeleteMapping("/removeAccessoryFromCart/{idAccessory}/{idCart}")
    public Cart removeAccessoryFromCart(@PathVariable("idAccessory") Long idAccessory, @PathVariable("idCart") Long idCart) {
        return cartServices.removeAccessoryFromCart(idAccessory, idCart);
    }

    @GetMapping("/cart/{idCart}/totalPrice")
    public float getTotalCartPrice(@PathVariable Long idCart) {
        return cartServices.getTotalCartPrice(idCart);
    }

    @DeleteMapping("/{idCart}/emptyCart")
    public void emptyCart(@PathVariable Long idCart) {
        cartServices.emptyCart(idCart);
    }

    @GetMapping("/{idCart}")
    public float calculateTotalCartPrice(@PathVariable Long idCart) {

        return cartServices.calculateTotalCartPrice(idCart);
    }
    @DeleteMapping("/deleteCart/{idCart}")

    public void deleteCart(@PathVariable("idCart") Long idCart) {
        cartServices.deleteCart(idCart);
    }


}