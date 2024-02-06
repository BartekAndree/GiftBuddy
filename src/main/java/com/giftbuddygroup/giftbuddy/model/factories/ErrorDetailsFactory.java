package com.giftbuddygroup.giftbuddy.model.factories;

import java.time.Instant;

import org.springframework.web.context.request.WebRequest;

import com.giftbuddygroup.giftbuddy.model.dto.response.ErrorDetailsDTO;

public class ErrorDetailsFactory {

    public static ErrorDetailsDTO buildErrorDetails(Exception exception, WebRequest request) {
        return ErrorDetailsDTO.builder()
                .timestamp(Instant.now())
                .message(exception.getMessage())
                .details(request.getDescription(false))
                .build();
    }
}
