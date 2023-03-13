package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.payment.PaymentDto;
import com.example.airbnbb7.payment.StripeServiceImpl;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentApi {
    @Autowired
    private StripeServiceImpl stripeService;

    @PostMapping("/charge")
    public SimpleResponse chargePayment(@RequestBody PaymentDto paymentDto) throws StripeException {
       return stripeService.chargePayments(paymentDto);
    }
}