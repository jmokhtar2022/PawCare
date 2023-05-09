package com.example.pawcare.controllers;

import com.example.pawcare.services.stripe.StripeService;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/stripe")
public class StripeController {
    @Autowired
    StripeService stripeService;

    //Create a stripe customer
    @PostMapping(value = "/payment/AddCustomer/{idUser}")
    public void createCustomer(@PathVariable("idUser") Long idUser) {
        stripeService.createStripeCustomer(idUser);
    }


    @GetMapping("/payment/retrieveCustomer/{idCus}")
    public String retrieveCustomer(@PathVariable("idCus") String idCus) {
        return stripeService.retrieveStripeCustomer(idCus);
    }


    @PostMapping(value = "/payment/createCardCustumorStripe/{customerId}/{card}/{expMonth}/{expYear}/{cvc}")
    @ResponseBody
    public String createCardCustumorStripe(@PathVariable("customerId") String customerId, @PathVariable("card") String card, @PathVariable("expMonth") String expMonth, @PathVariable("expYear") String expYear, @PathVariable("cvc") String cvc) throws StripeException {
        return stripeService.createCardForCustumorStripe(customerId, card, expMonth, expYear, cvc);
    }


    public ResponseEntity<String> chargeCustomer(@RequestParam String customerId, @RequestParam int amount) {
        stripeService.chargeCustomer(customerId, amount);
        return ResponseEntity.ok("Charge successful.");

    }
}