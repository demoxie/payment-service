package com.saultech.paymentservice.controller;

import com.saultech.paymentservice.dto.request.PaymentDto;
import com.saultech.paymentservice.dto.response.APIResponse;
import com.saultech.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kong.unirest.core.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.saultech.paymentservice.constant.Routes.PAYMENT_BASE_ROUTE;


@Tag(name = "Payment", description = "Payment API")
@RestController
@RequestMapping(PAYMENT_BASE_ROUTE)
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/")
    public String index(){
        return "Hello";
    }
    @PostMapping("/initialize")
    public ResponseEntity<APIResponse> initializePayment(@Valid @RequestBody PaymentDto request){
        return paymentService.makePayment(request);
    }
    @GetMapping("/verify-payment")
    public ResponseEntity<APIResponse> verifyPayment(@RequestParam(required = true) String reference){
        return paymentService.verifyPayment(reference);
    }
    @GetMapping("/get-all")
    public ResponseEntity<APIResponse> retrieveAllATransactions(){
        return paymentService.retrieveAllATransactions();
    }

}
