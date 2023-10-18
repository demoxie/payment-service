package com.saultech.paymentservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PaymentServiceApplicationTests {
    @Autowired private PaymentServiceApplication paymentServiceApplication;

    @Test
    void contextLoads() {
        assertThat(paymentServiceApplication).isNotNull();
    }

}
