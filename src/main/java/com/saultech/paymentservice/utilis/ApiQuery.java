package com.saultech.paymentservice.utilis;

import com.saultech.paymentservice.config.paystack.PaystackProperties;
import kong.unirest.core.Unirest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApiQuery {
    private final PaystackProperties properties;
    public static Map<String, Object> getWithQueryParams(String path, Map<String, Object> params){
        if (params == null) {
            return Unirest.get(path)
                    .asJson().getBody().getObject().toMap();
        }
        return Unirest.get(path)
                .queryString(params)
                .asJson().getBody().getObject().toMap();
    }

    public static Map<String, Object> getWithRouteParams(String path,Map<String, Object> params){
        if (params == null) {
            return Unirest.get(path)
                    .asJson().getBody().getObject().toMap();
        }
        return Unirest.get(path)
                .routeParam(params)
                .asJson().getBody().getObject().toMap();
    }

    public static Map<String, Object> postWithBody(String path, Object body){
        if (body == null) {
            return Unirest.post(path)
                    .asJson().getBody().getObject().toMap();
        }
        return Unirest.post(path)
                .body(body)
                .asJson().getBody().getObject().toMap();
    }

    public static Map<String, Object> putWithBody(String path, Object body){
        if (body == null) {
            return Unirest.put(path)
                    .asJson().getBody().getObject().toMap();
        }
        return Unirest.put(path)
                .body(body)
                .asJson().getBody().getObject().toMap();
    }
}
