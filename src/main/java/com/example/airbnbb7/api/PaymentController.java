package com.example.airbnbb7.api;

import com.example.airbnbb7.payment.PaymentDto;
import com.example.airbnbb7.payment.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private StripeService stripeService;

    @PostMapping("/charge")
    public ResponseEntity<?> chargePayment(@RequestBody PaymentDto paymentDto) throws StripeException {
       return stripeService.chargePayment(paymentDto);
    }
}