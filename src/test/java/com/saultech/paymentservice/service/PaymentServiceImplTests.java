package com.saultech.paymentservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saultech.paymentservice.config.paystack.PaystackProperties;
import com.saultech.paymentservice.dto.request.PaymentDto;
import com.saultech.paymentservice.dto.response.APIResponse;
import com.saultech.paymentservice.entity.Transaction;
import com.saultech.paymentservice.repository.TransactionRepository;
import com.saultech.paymentservice.utilis.ApiQuery;
import kong.unirest.core.JsonNode;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PaymentServiceImpl.class, ModelMapper.class, ObjectMapper.class, TransactionRepository.class, PaystackProperties.class})
@ActiveProfiles("test")
class PaymentServiceImplTests {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentServiceImpl paymentServiceImpl;

    @MockBean
    private PaystackProperties paystackProperties;

    private MockedStatic<ApiQuery> apiQueryMockedStatic;

    @BeforeEach
    void setUp() {
        apiQueryMockedStatic = Mockito.mockStatic(ApiQuery.class);
    }

    @AfterEach
    void tearDown() {
        apiQueryMockedStatic.close();
    }

    /**
     * Method under test: {@link PaymentServiceImpl#makePayment(PaymentDto)}
     */
    @Test
    @SneakyThrows
    void testMakePayment() {
        PaymentDto request = new PaymentDto();
        request.setAmount("1000");
        request.setEmail("test@example");

        HashMap<String, Object> result = new HashMap<>();
        result.put("reference", "Reference");
        JsonNode jsonNode = new JsonNode("{\"reference\":\"Reference\",\"access_code\":\"Access Code\"}");
        result.put("data", jsonNode);
        apiQueryMockedStatic.when(() -> ApiQuery.postWithBody("/transaction/initialize", request)).thenReturn(result);
        when(objectMapper.convertValue(any(Object.class), ArgumentMatchers.<Class<HashMap>>any())).thenReturn(new HashMap<>());
        when(modelMapper.map(any(), any())).thenReturn(new Transaction());
        when(transactionRepository.save(any())).thenReturn(new Transaction());

        ResponseEntity<APIResponse> response = paymentServiceImpl.makePayment(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getData()).isInstanceOf(HashMap.class);
    }

    /**
     * Method under test: {@link PaymentServiceImpl#verifyPayment(String)}
     */
    @Test
    @SneakyThrows
    void testVerifyPayment() {
        apiQueryMockedStatic.when(() -> ApiQuery.getWithRouteParams("/transaction/verify/{reference}", null)).thenReturn(null);
        ResponseEntity<APIResponse> response = paymentServiceImpl.verifyPayment("Reference");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    /**
     * Method under test: {@link PaymentServiceImpl#retrieveAllATransactions()}
     */
    @Test
    @SneakyThrows
    void testRetrieveAllATransactions() {
        apiQueryMockedStatic.when(() -> ApiQuery.getWithRouteParams("/transaction", null)).thenReturn(null);
        ResponseEntity<APIResponse> response = paymentServiceImpl.retrieveAllATransactions();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

