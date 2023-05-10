package com.example.pawcare.services.cart;

<<<<<<< HEAD
=======
import com.example.pawcare.services.email.MailService;
>>>>>>> 6a9f49c22009bf5553cb1702dc1d4d30cdba0fd2
import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Cart;
import com.example.pawcare.entities.Item;
import com.example.pawcare.repositories.IAccessoryRepository;
import com.example.pawcare.repositories.ICartRepository;
import com.example.pawcare.repositories.auth.IUserRepository;
<<<<<<< HEAD
import com.example.pawcare.services.email.MailService;
=======
>>>>>>> 6a9f49c22009bf5553cb1702dc1d4d30cdba0fd2
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

    /*@Override
    public Cart assignCart(Cart cart, Long userId) {
        User user = iUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        cart.setUser(user); // associer le panier à l'utilisateur
        return iCartRepository.save(cart); }
*/
    @Override
    public Cart addCart(Cart cart){
        return  iCartRepository.save(cart);
    }

    @Override
    public Cart GetCartById(Long idCart) {
        return iCartRepository.findById(idCart).get();
    }

    @Override
    public Cart createCart() {
        Cart cart = new Cart();
        cart.setTotalCart(0.0f); // initialize
        cart.setItem(null);
        return iCartRepository.save(cart);

    }

    @Override
    public void deleteCart(Long idCart) {
        iCartRepository.deleteById(idCart);
    }

    @Override
    public Cart addAccessoryToCart(Long idAccessory,Long idCart)
    {
        Cart cart =iCartRepository.findById(idCart).get();
        Accessory ac = iAccessoryRepository.findById(idAccessory).get();
        List<Accessory> accesories  = cart.getAccessories();
        accesories.add(ac);
        cart.setAccessories(accesories);
        // Update the totalCart value
        float totalCartPrice = cart.getTotalCart() + ac.getPrice();
        cart.setTotalCart(totalCartPrice);
        // Update the totalItem value
        //float totalItemPrice = cart.getTotalCart() + cart.getItem().getDeliveryPrice();
        //cart.getItem().setTotalItem(totalItemPrice);
        return iCartRepository.save(cart);
    }
    @Override
    public Cart updateCart(Cart cart, Long idCart) {
        return iCartRepository.save(cart);

    }
    @Override
    public Cart removeAccessoryFromCart(Long idAccessory,Long idCart)
    {
        Cart cart =iCartRepository.findById(idCart).get();
        Accessory ac = iAccessoryRepository.findById(idAccessory).get();
        List<Accessory> accesories  = cart.getAccessories();
        accesories.remove(ac);
        cart.setAccessories(accesories);
        // Update the totalCart value
        float totalCartPrice = cart.getTotalCart() - ac.getPrice();
        cart.setTotalCart(totalCartPrice);
        // Update the totalItem value
        //float totalItemPrice = cart.getTotalCart() + cart.getItem().getDeliveryPrice();
        //cart.getItem().setTotalItem(totalItemPrice);

        return iCartRepository.save(cart);
    }
    @Override
    public Cart displayCartByCartId(Long idCart)
    {
        return GetCartById(idCart);
    }
    @Override
    public float getTotalCartPrice(Long idCart) {
    Cart cart = iCartRepository.findById(idCart).orElseThrow(() -> new EntityNotFoundException("Cart not found with id " + idCart));
    List<Accessory> accessories = cart.getAccessories();
    float totalPrice = 0;
    for (Accessory accessory : accessories) {
        totalPrice += accessory.getPrice();
    }
    return totalPrice;
}
    @Override
    public float calculateTotalCartPrice( Long idCart) {

        Cart cart = iCartRepository.findById(idCart).orElseThrow(() -> new RuntimeException("Cart not found"));

        float totalCartPrice = 0;
        for (Accessory accessory : cart.getAccessories()) {
            totalCartPrice += accessory.getPrice();
        }
        cart.setTotalCart(totalCartPrice);
        iCartRepository.save(cart);
        return totalCartPrice;
    }
    public void emptyCart(Long cartId) {
        Cart cart = iCartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        cart.setAccessories(new ArrayList<>());
        cart.setTotalCart(0.0f); // Set totalCart to 0
        cart.getItem().setTotalItem(0.0f); // Set totalItem to 0
        iCartRepository.save(cart);
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


