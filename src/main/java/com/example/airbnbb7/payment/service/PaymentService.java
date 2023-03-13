package com.example.airbnbb7.payment.service;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.payment.PaymentDto;

public interface PaymentService {

    SimpleResponse chargePayments(PaymentDto paymentDto);

}
