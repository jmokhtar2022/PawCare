package com.example.pawcare.services.stripe;

import com.stripe.exception.StripeException;

public interface StripeService  {


    public String createStripeCustomer(Long idUser);
    public String retrieveStripeCustomer(String idCus);
    public String createCardForCustumorStripe(String customerId, String carta, String expMonth, String expYear, String cvc) throws StripeException;
    public void chargeCustomer(String customerId,int amount);
}
