package com.saultech.paymentservice.config.paystack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class PaystackProperties {
    @Value("${paystack.secret-key}")
    public String secretKey;

    @Value("${paystack.public-key}")
    public String publicKey;

    @Value("${paystack.base-url}")
    public String baseUrl;
}
