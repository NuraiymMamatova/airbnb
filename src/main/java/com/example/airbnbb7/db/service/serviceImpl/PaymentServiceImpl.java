package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.service.PaymentService;
import com.example.airbnbb7.dto.request.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    public SimpleResponse chargePayments(PaymentRequest paymentDto) {
        try {
            Stripe.apiKey = secretKey;
            int amount = Math.round(paymentDto.getAmount() * 100);
            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount);
            params.put("currency", paymentDto.getCurrency());
            params.put("source", paymentDto.getToken());
            Charge.create(params);
            log.info("Successfully the charge credit card method works");
            return new SimpleResponse("Payment successful!");
        } catch (StripeException e) {
            log.error("Something wrong with token");
            return new SimpleResponse(e.getMessage());
        }

    }
}
