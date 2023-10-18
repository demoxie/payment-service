package com.saultech.paymentservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Builder
public class APIException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    final HttpStatus status;
    final String message;
}
