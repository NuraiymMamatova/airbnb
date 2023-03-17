package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.dto.request.PaymentRequest;

public interface PaymentService {

    SimpleResponse chargePayments(PaymentRequest payment);

}
