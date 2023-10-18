package com.saultech.paymentservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIError {
    private String timestamp;
    private String path;
    private String errors;
    private String status;
    private String message;
}
