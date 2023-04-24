package com.example.pawcare.services.cart;

import com.example.pawcare.services.email.MailService;
import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Cart;
import com.example.pawcare.entities.Item;
import com.example.pawcare.entities.User;
import com.example.pawcare.repositories.IAccessoryRepository;
import com.example.pawcare.repositories.ICartRepository;
import com.example.pawcare.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
    @Autowired
    MailService mailService;

    @Override
    public Cart assignCart(Cart cart, Long userId) {
        User user = iUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        cart.setUser(user); // associer le panier à l'utilisateur
        return iCartRepository.save(cart); }

    @Override
    public Cart addCart(Cart cart){
        return  iCartRepository.save(cart);
    }

    @Override
    public Cart GetCartById(Long idCart) {
        return iCartRepository.findById(idCart).get();
    }

    public Cart createCart() {
        Cart cart = new Cart();
       // cart.setIdCart(1);
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
    public Cart updateCart(Cart cart, Long idCart) {
        return iCartRepository.save(cart);

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
@Override
public float getTotalCartPrice(Long cartId) {
    Cart cart = iCartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found with id " + cartId));
    List<Accessory> accessories = cart.getAccessories();
    float totalPrice = 0;
    for (Accessory accessory : accessories) {
        totalPrice += accessory.getPrice();
    }
    return totalPrice;
}
public void emptyCart(Long cartId) {
    Cart cart = iCartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    cart.setAccessories(new ArrayList<>());
    iCartRepository.save(cart);
}

@Override
    public float calculateTotalCartPrice( Long cartId) {

        Cart cart = iCartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        float totalCartPrice = 0;
        for (Accessory accessory : cart.getAccessories()) {
            totalCartPrice += accessory.getPrice();
        }
        cart.setTotalCart(totalCartPrice);
        iCartRepository.save(cart);
        // Return the calculated total price as a float
        return totalCartPrice;
    }
    @Override
    public ResponseEntity<String> confirmOrder(@RequestBody Item order) {
        float totalPrice = calculateTotalCartPrice(order.getCart().getIdCart());
        String to = order.getUser().getEmail();
        String subject = "Thank you for your purchase";
        String body = "Your total price is " + totalPrice + ". Thank you for your purchase!";
        mailService.sendEmail(to,subject,body);
        return ResponseEntity.ok("Order confirmed successfully");
    }



}


