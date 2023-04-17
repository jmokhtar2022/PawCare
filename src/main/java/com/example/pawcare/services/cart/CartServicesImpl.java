package com.example.pawcare.services.cart;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Cart;
import com.example.pawcare.entities.User;
import com.example.pawcare.repositories.IAccessoryRepository;
import com.example.pawcare.repositories.ICartRepository;
import com.example.pawcare.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CartServicesImpl implements CartServices {
    private EntityManager entityManager;

    @Autowired
    ICartRepository iCartRepository;
    @Autowired
    IAccessoryRepository iAccessoryRepository;
    @Autowired
    IUserRepository iUserRepository;

    @Override
    public List<Cart> retrieveAllCarts() {

        return iCartRepository.findAll();

    }

    @Override
    public Cart addCart(Cart cart, Long userId) {
        User user = iUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        cart.setUser(user); // associer le panier à l'utilisateur
        return iCartRepository.save(cart); }
/*
    @Override
    public Cart addCart(Cart cart){
        return  iCartRepository.save(cart);
    }*/
    @Override
    public Cart updateCart(Cart cart, Long idCart) {
        return iCartRepository.save(cart);

    }
    @Override
    public void deleteCart(Long idCart) {
        iCartRepository.deleteById(idCart);
    }
    @Override
    public Cart retrieveCartById(Long idCart) {
        return iCartRepository.findById(idCart).get();
    }


    public Cart createCart() {
        Cart cart = new Cart();
        cart.setTotalCart(0.0f); // initialize the totalCart to zero
        return iCartRepository.save(cart);
    }
    @Override
    public Cart addAccessoryToCart(Long idAccessory,Long idCart)
    {
        Cart c =iCartRepository.findById(idCart).get();
        Accessory ac = iAccessoryRepository.findById(idAccessory).get();
        List<Accessory> accesories  = c.getAccessories();
        accesories.add(ac);
        c.setAccessories(accesories);
        return iCartRepository.save(c);
    }
    @Override
    public Cart removeAccessoryFromCart(Long idAccessory,Long idCart)
    {
        Cart c =iCartRepository.findById(idCart).get();
        Accessory ac = iAccessoryRepository.findById(idAccessory).get();
        List<Accessory> accesories  = c.getAccessories();
        accesories.remove(ac);
        c.setAccessories(accesories);
        return iCartRepository.save(c);
    }
    @Override
    public Cart displayCartByUserId(Long id)
    {
        return iCartRepository.getCart(id);
    }


}
