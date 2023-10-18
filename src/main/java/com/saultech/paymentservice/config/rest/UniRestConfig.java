package com.saultech.paymentservice.config.rest;

import com.saultech.paymentservice.config.paystack.PaystackProperties;
import kong.unirest.core.Unirest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UniRestConfig {
    private final PaystackProperties properties;

    @Bean
    public void unirest(){
        log.info("SECRET KEY {}",properties.secretKey);
        Unirest.config()
//                 .connectTimeout(50000)
//                 .cacheResponses(true)
//                 .cacheResponses(new Cache.Builder()
//                         .depth(5)
//                         .maxAge(5, TimeUnit.MINUTES)
//                 )
                .setDefaultHeader("Authorization","Bearer " +properties.secretKey)
                 .setDefaultHeader("Accept","application/json")
                 .defaultBaseUrl(properties.baseUrl);
    }
}
