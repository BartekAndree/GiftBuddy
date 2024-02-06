package com.giftbuddygroup.giftbuddy.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {
    private UUID id;
    private UUID organizerId;
    private UUID organizerExternalId;
    private String title;
    private String description;
    private String giftIdea;
    private String imageUrl;
    private BigDecimal contribution;
    private BigDecimal currentAmount;
    private BigDecimal targetAmount;
    private LocalDate eventDate;
    private LocalDate endDate;
    private Boolean isActive;
}
