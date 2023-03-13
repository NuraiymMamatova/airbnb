package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.dto.request.PaymentRequest;
import com.example.airbnbb7.db.service.serviceImpl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentApi {
    @Autowired
    private PaymentServiceImpl stripeService;

    @PostMapping("/charge")
    public SimpleResponse chargePayment(@RequestBody PaymentRequest paymentDto){
       return stripeService.chargePayments(paymentDto);
    }
}