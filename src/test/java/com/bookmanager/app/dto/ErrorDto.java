package com.bookmanager.app.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDto {
    private String error;
    private String timestamp;
    private HttpStatus status;
}
