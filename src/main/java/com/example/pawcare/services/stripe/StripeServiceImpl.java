package com.example.pawcare.services.stripe;
import com.example.pawcare.entities.*;
import com.example.pawcare.repositories.IUserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
@Slf4j
public class StripeServiceImpl implements StripeService{
    @Autowired
    IUserRepository iUserRepository;

    @Override
    public String createStripeCustomer(Long idUser) {
        Stripe.apiKey = "sk_test_51N2AKQBKAkRPCxsKoph61XdywTfWSW9Q2XNr7zxhYIXkLozwrGATODSkJ928QgIwGocTJOvLxzW3HRaxhHR1sppy00GxTiuU0n";
        User user = iUserRepository.findUserById(idUser);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("description", "My First Test Customer (created for API docs)");
        params.put("email", user.getEmail());
        try {
            Customer customer = Customer.create(params);
            log.info("5555555555555Stripe customer with ID {} created successfully", customer.getId());

            return customer.getId();
        } catch (StripeException e) {
            log.error("5555555555555Error creating Stripe customer", e);

            throw new RuntimeException(e);
        }

    }


    @Override
    public String retrieveStripeCustomer(String idCus) {
        log.info("88888888888888Retrieving Stripe customer with ID {}", idCus);

        return " okiiii"+idCus;
    }
@Override
    public String createCardForCustumorStripe(String customerId, String card, String expMonth, String expYear,
                                              String cvc) throws StripeException {
        Stripe.apiKey = "sk_test_51N2AKQBKAkRPCxsKoph61XdywTfWSW9Q2XNr7zxhYIXkLozwrGATODSkJ928QgIwGocTJOvLxzW3HRaxhHR1sppy00GxTiuU0n";
        Customer customer = Customer.retrieve(customerId);
    if (customer == null) {
        log.error("5555555555555Failed to retrieve customer with ID: {}", customerId);
        return null;
    }
    log.info("88888888888888Retrieving Stripe customer with ID {}",  customerId);

    Map<String, Object> cardParam = new HashMap<String, Object>();
        cardParam.put("number", card);
        cardParam.put("exp_month", expMonth);
        cardParam.put("exp_year", expYear);
        cardParam.put("cvc", cvc);
        Map<String, Object> tokenParam = new HashMap<String, Object>();
        tokenParam.put("card", cardParam);

        Token token = Token.create(tokenParam);
        Map<String, Object> source = new HashMap<String, Object>();
        source.put("source", token.getId());
    log.info("44444444444Source: {}", source);

    try {
        if (customer == null) {
            log.error("2222222222Failed to retrieve customer with ID: {}", customerId);
            return null;
        }

        customer.getSources().create(source);
        log.info("4444444444Successfully created source for customer with ID: {}", customerId);
        return token.getId();
        } catch (StripeException e) {
        log.error("444444444444444Failed to create source for customer with ID: {}", customerId, e);
        throw e;
    }

    }
    @Override
    public void chargeCustomer(String customerId, int amount) {
        Stripe.apiKey = "sk_test_51N2AKQBKAkRPCxsKoph61XdywTfWSW9Q2XNr7zxhYIXkLozwrGATODSkJ928QgIwGocTJOvLxzW3HRaxhHR1sppy00GxTiuU0n";
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", "usd");
        params.put("customer", customerId);

        try {
           Charge.create(params);
            log.info("55555555Stripe customer with ID {} charged successfully for amount {}", customerId, amount);

        } catch (StripeException e) {
            log.error("555555555Error charging Stripe customer with ID {} for amount {}", customerId, amount, e);
            throw new RuntimeException(e);
        }

    }



                }