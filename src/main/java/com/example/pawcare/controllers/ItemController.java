package com.example.pawcare.controllers;

import com.example.pawcare.services.email.MailService;
import com.example.pawcare.entities.Item;
import com.example.pawcare.services.cart.CartServices;
import com.example.pawcare.services.item.IItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")

public class ItemController {
    @Autowired
    IItemServices iItemServices;
    @Autowired
    private MailService emailService;
    @Autowired
    private CartServices cartServices;
    @GetMapping("/itemslist")
    public List<Item> retrieveAllItems()
    {
        return iItemServices.retrieveAllItems();
    }


    @PostMapping("/additem")
    public Item addItem(@RequestBody Item item) {
        return iItemServices.addItem(item);

    }
    @PutMapping(value = "/updateOrder/{idItem}")

    public Item updateItem(@PathVariable Long idItem, @RequestBody Item item) {
        return iItemServices.updateItem(item,idItem);

    }

    @DeleteMapping("/deleteOrder/{idItem}")

    public void deleteItem(@PathVariable("idItem") Long idItem) {
        iItemServices.deleteItem(idItem);
    }

    @GetMapping("/GetOrderById/{idItem}")
    public Item retrieveItemById(@PathVariable("idItem") Long idItem) {
        return iItemServices.retrieveItemById(idItem);
    }

    @GetMapping("/{idItem}/pdf")
    public void generateBillPdf(@PathVariable Long idItem, HttpServletResponse response) throws IOException {
        Item item = iItemServices.retrieveItemById(idItem);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=item.pdf");

        byte[] pdfBytes = iItemServices.generateBillPdf(item);
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }

    @PostMapping("/createOrder/{idCart}")
    public ResponseEntity<Item> createOrder(@RequestBody Item order, @PathVariable("idCart") Long idCart) {
     return iItemServices.createOrder(order,idCart);
    }


    }
