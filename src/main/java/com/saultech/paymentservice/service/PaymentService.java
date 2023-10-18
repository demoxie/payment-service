package com.saultech.paymentservice.service;

import com.saultech.paymentservice.dto.response.APIResponse;
import org.springframework.http.ResponseEntity;
import com.saultech.paymentservice.dto.request.PaymentDto;

import java.util.Map;

public interface PaymentService {
    ResponseEntity<APIResponse> makePayment(PaymentDto request);

    ResponseEntity<APIResponse> verifyPayment(String reference);

    ResponseEntity<APIResponse> retrieveAllATransactions();
}
