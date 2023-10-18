package com.saultech.paymentservice.controller;

import com.saultech.paymentservice.dto.request.PaymentDto;
import com.saultech.paymentservice.helpers.TestHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PaymentControllerTest {
    @Autowired private PaymentController paymentController;
    @Autowired private TestRestTemplate restTemplate;
    @Autowired private MockMvc mockMvc;
    @Autowired private TestHelper testHelper;
    private final String baseUrl = "http://localhost:8200";
    private final String baseRoute = "/api/v1/payments";
    @Test
    void contextLoads() {
        assertThat(paymentController).isNotNull();
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    @SneakyThrows
    void initializePayment() {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setAmount("1000");
        paymentDto.setEmail("test@example.com");
        mockMvc.perform(
                post(baseRoute + "/initialize")
                        .contentType("application/json")
                        .content(testHelper.asJsonString(paymentDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"status\":\"200\",\"message\":\"Payment successful\"}"
                ));
    }
    @Test
    @SneakyThrows
    void initializePaymentWithInvalidEmail() {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setAmount("1000");
        paymentDto.setEmail("testexample.com");
        mockMvc.perform(
                post(baseRoute + "/initialize")
                        .contentType("application/json")
                        .content(testHelper.asJsonString(paymentDto))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "{\"status\":\"400\",\"message\":\"Invalid email\"}"
                ));
    }

    @Test
    @SneakyThrows
    void verifyPayment() {
        String reference = "6ew4ha1ogj";
        String url = baseUrl + baseRoute + "/verify-payment";
        mockMvc.perform(get(url)
                .contentType("application/json")
                        .queryParam("reference", reference))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"status\":\"200\",\"message\":\"Valid Transaction\"}"
                ));
    }
    @Test
    @SneakyThrows
    void verifyPaymentWithInvalidReference() {
        String reference = "";
        String url = baseUrl + baseRoute + "/verify-payment";
        mockMvc.perform(get(url)
                .contentType("application/json")
                .queryParam("reference", reference))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"status\":\"200\",\"message\":\"Invalid Transaction\"}"
                ));
    }

    @Test
    @SneakyThrows
    void retrieveAllATransactions() {
        String url = baseUrl + baseRoute + "/get-all";
        mockMvc.perform(get(url)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"status\":\"200\",\"message\":\"Fetched successfully\"}"
                ));
    }

    @Test
    void index() {
    }
}