package com.example.trileucotestapp.error;

import lombok.Data;

@Data
public class CustomErrorResponse {

    private String message;
    private int status;

    public CustomErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
