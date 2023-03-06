package com.example.airbnbb7.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StripeService {

    @Value("${STRIPE_SECRET_KEY}")
    private  String secretKey;

    public Charge chargePayments(PaymentDto paymentDto) throws StripeException {
        Stripe.apiKey = secretKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentDto.getAmount() * 100L);
        params.put("currency", paymentDto.getCurrency());
        params.put("source", paymentDto.getToken());

        return Charge.create(params);
    }

    public ResponseEntity<?> chargePayment(PaymentDto paymentDto) throws StripeException {
        Charge charge = chargePayments(paymentDto);

        if (charge.getStatus().equals("succeeded")) {
            return ResponseEntity.ok(charge);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(charge);
        }
    }
}
