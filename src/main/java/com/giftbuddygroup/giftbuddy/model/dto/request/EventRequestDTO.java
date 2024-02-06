package com.giftbuddygroup.giftbuddy.model.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDTO {

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 4, max = 100, message = "Title must be between 4 and 100 characters")
    private String title;

    @Size(min = 4, max = 500, message = "Description must be between 4 and 500 characters")
    private String description;

    @Size(max = 100, message = "Gift idea cannot be longer than 100 characters")
    private String giftIdea;

    @Size(max = 255, message = "Image URL cannot be longer than 255 characters")
    private String imageUrl;

    @DecimalMin(value = "0.0", inclusive = false, message = "Contribution must be positive")
    private BigDecimal contribution;

    @DecimalMin(value = "0.0", inclusive = false, message = "Target amount must be positive")
    private BigDecimal targetAmount;

    @FutureOrPresent(message = "Event date must be in the future or present")
    private LocalDate eventDate;

    @NotNull(message = "End date cannot be null")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;
}
