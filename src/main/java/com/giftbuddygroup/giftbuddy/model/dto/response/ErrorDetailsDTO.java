package com.giftbuddygroup.giftbuddy.model.dto.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorDetailsDTO {
    private Instant timestamp;
    private String message;
    private String details;
}
