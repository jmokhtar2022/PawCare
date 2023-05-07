package com.example.pawcare.controllers;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.User;
import com.example.pawcare.services.email.MailService;
import com.example.pawcare.entities.Item;
import com.example.pawcare.services.cart.CartServices;
import com.example.pawcare.services.item.IItemServices;
import com.example.pawcare.services.item.ItemReports.ItemReportServices;
import com.example.pawcare.services.stripe.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")

public class ItemController {
    @Autowired
    IItemServices iItemServices;
    @Autowired
    private MailService emailService;
    @Autowired
    private CartServices cartServices;
    @Autowired
    StripeService stripeService;
    @Autowired
    ItemReportServices itemReportServices;
    @GetMapping("/itemslist")
    public List<Item> retrieveAllItems() {
        return iItemServices.retrieveAllItems();
    }


    @PostMapping("/additem")
    public Item addItem(@RequestBody Item item) {
        return iItemServices.addItem(item);

    }

    @PutMapping(value = "/updateOrder/{idItem}")

    public Item updateItem(@PathVariable Long idItem, @RequestBody Item item) {
        return iItemServices.updateItem(item, idItem);

    }

    @DeleteMapping("/deleteOrder/{idItem}")

    public void deleteItem(@PathVariable("idItem") Long idItem) {
        iItemServices.deleteItem(idItem);
    }

    @GetMapping("/GetOrderById/{idItem}")
    public Item retrieveItemById(@PathVariable("idItem") Long idItem) {
        return iItemServices.retrieveItemById(idItem);
    }


    @PostMapping("/createOrder/{idCart}/{idUser}")
    public ResponseEntity<Item> createOrderFromCart(@PathVariable("idCart") Long idCart, @PathVariable("idUser") Long idUser) {
        return iItemServices.createOrderFromCart(idCart, idUser);
    }

    @PostMapping("/payOrder/{orderId}")
    public Map<String, Object> payOrder(@PathVariable("orderId") Long orderId, @RequestParam("idUser") Long userId,
                                        @RequestParam("card") String cardNumber,
                                        @RequestParam("expMonth") String expMonth,
                                        @RequestParam("expYear") String expYear,
                                        @RequestParam("cvc") String cvc) throws StripeException {
        return iItemServices.payOrder(orderId, userId, cardNumber, expMonth, expYear, cvc);
    }


    @PutMapping("/updateStatusOrder/{orderId}")
    public void updateOrderStatusAsPaid(@PathVariable Long orderId) {

        iItemServices.updateOrderStatusAsPaid(orderId);
    }

    @GetMapping("/{orderId}/accessories")
    public ResponseEntity<List<Accessory>> getAccessoriesByOrderId(@PathVariable Long orderId) {
        List<Accessory> accessories = iItemServices.getAccessoriesByOrderId(orderId);

        if (accessories.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(accessories);
        }
    }

    @GetMapping("/{orderId}/user")
    public ResponseEntity<User> getUserByOrderId(@PathVariable Long orderId) {
        User user = iItemServices.getUserByIdOrder(orderId);
        if (user == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(user);
        }


    }

    @GetMapping("/bill/{itemId}")
    public ResponseEntity<byte[]> generateBillPdf(@PathVariable Long itemId) throws IOException {
       // Item item = iItemServices.retrieveItemById(itemId);
        byte[] pdfBytes = iItemServices.generateBillPdf(itemId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("bill.pdf").build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    @GetMapping("/daily")
    public void getDailyRevenueReport() {
         itemReportServices.generateDailyRevenueReport();
    }

}

