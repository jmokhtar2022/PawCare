package com.example.pawcare.controllers;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Cart;
import com.example.pawcare.entities.User;
import com.example.pawcare.services.accessory.AccessoryServices;
import com.example.pawcare.services.cart.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("cart")
public class CartController {
    @Autowired
    CartServices cartServices;

    @GetMapping("/carts")
    public List<Cart> showcartlist() {
        return cartServices.retrieveAllCarts();
    }

    @PostMapping("/addCart/{id}") //id for idUser
    public Cart addCart(@RequestBody Cart cart, @PathVariable("id") Long id) {
        return cartServices.addCart(cart,id);

    }
    /*@PostMapping("/addCart")
    public Cart addCart(@RequestBody Cart cart){
        return cartServices.addCart(cart);
    }*/

    @PutMapping(value = "/updateCart/{idCart}")

    public Cart updateCart(@PathVariable Long idCart, @RequestBody Cart cart) {
        return cartServices.updateCart(cart, idCart);

    }
    @GetMapping("/displayCartByUserId/{id}")
    public Cart displayCartByUserId(@PathVariable("id") Long id) //id for idUser
    {
        return cartServices.displayCartByUserId(id);
    }

    @GetMapping("/getCartById/{idCart}")
    public Cart GetCartById(@PathVariable("idCart") Long idCart) {
        return cartServices.retrieveCartById(idCart);
    }
   @PutMapping("/addAccessoryToCart/{idAccessory}/{idCart}")
   public Cart addAccessoryToCart(@PathVariable("idAccessory") Long idAccessory,@PathVariable("idCart") Long idCart)
   {
       return cartServices.addAccessoryToCart(idAccessory, idCart);
   }

    @DeleteMapping("/removeAccessoryFromCart/{idAccessory}/{idCart}")
    public Cart removeAccessoryFromCart(@PathVariable("idAccessory") Long idAccessory,@PathVariable("idCart") Long idCart)
    {    return cartServices.removeAccessoryFromCart(idAccessory, idCart);
    }



}
