package com.saultech.paymentservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saultech.paymentservice.config.paystack.PaystackProperties;
import com.saultech.paymentservice.dto.request.PaymentDto;
import com.saultech.paymentservice.dto.response.APIResponse;
import com.saultech.paymentservice.entity.Transaction;
import com.saultech.paymentservice.repository.TransactionRepository;
import com.saultech.paymentservice.utilis.ApiQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService{
    private final ObjectMapper mapper;
    private final PaystackProperties properties;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;


    @Override
    public ResponseEntity<APIResponse> makePayment(PaymentDto request) {
        var result = ApiQuery.postWithBody("/transaction/initialize", request);
        HashMap data = mapper.convertValue(result.get("data"), HashMap.class);
        Transaction transaction = modelMapper.map(request, Transaction.class);
        String reference = (String) data.get("reference");
        if(Strings.isNotEmpty(reference)){
            transaction.setReference(reference);
            transaction.setIsSuccessful(true);
        }else{
            transaction.setReference(reference);
            transaction.setIsSuccessful(false);
        }
        transactionRepository.save(transaction);

        return ResponseEntity.ok(
                APIResponse.builder()
                        .data(data)
                        .message("Payment successful")
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .build());
    }

    @Override
    public ResponseEntity<APIResponse> verifyPayment(String reference) {
        var response = ApiQuery.getWithRouteParams("/transaction/verify/{reference}", Map.of("reference", reference));
        Object result = null;
        if (!Objects.isNull(response) && response.containsKey("data")) {
            result = response.get("data");
            return ResponseEntity.ok(
                    APIResponse.builder()
                            .data(result)
                            .message("Valid Transaction")
                            .status(String.valueOf(HttpStatus.OK.value()))
                            .build());
        }
        return ResponseEntity.ok(
                APIResponse.builder()
                        .data(result)
                        .message("Invalid Transaction")
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .build());
    }

    @Override
    public ResponseEntity<APIResponse> retrieveAllATransactions() {
        var result = ApiQuery.getWithQueryParams("/transaction", null);
        if (!Objects.isNull(result) && result.containsKey("data")) {
            return ResponseEntity.ok(
                    APIResponse.builder()
                            .data(result)
                            .message("Fetched successfully")
                            .status(String.valueOf(HttpStatus.OK.value()))
                            .build());
        }

        return ResponseEntity.ok(
                APIResponse.builder()
                        .data(null)
                        .message("No transaction found")
                        .status(String.valueOf(HttpStatus.OK.value()))
                        .build()
        );
    }
}
