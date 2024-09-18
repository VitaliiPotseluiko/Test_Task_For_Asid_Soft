package com.bookmanager.app.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
public class ErrorValidationDto {
    private String[] errors;
    private LocalDateTime timeStamp;
    private HttpStatus status;
}