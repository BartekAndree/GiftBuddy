package com.giftbuddygroup.giftbuddy.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;

@Entity
@Table(name = "event")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Event extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private UserProfile organizer;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Size(max = 500, message = "Description cannot be longer than 500 characters")
    @Column(name = "description", length = 500)
    private String description;

    @Size(max = 100, message = "Gift idea cannot be longer than 100 characters")
    @Column(name = "gift_idea", length = 100)
    private String giftIdea;

    @Size(max = 255, message = "Image URL cannot be longer than 255 characters")
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @DecimalMin(value = "0.0", inclusive = false, message = "Contribution must be positive")
    @Column(name = "contribution", precision = 8, scale = 2)
    private BigDecimal contribution;

    @Column(name = "current_amount", precision = 8, scale = 2)
    private BigDecimal currentAmount;

    @DecimalMin(value = "0.0", inclusive = false, message = "Target amount must be positive")
    @Column(name = "target_amount", precision = 8, scale = 2)
    private BigDecimal targetAmount;

    @FutureOrPresent(message = "Event date must be in the future or present")
    @Column(name = "event_date")
    private LocalDate eventDate;

    @NotNull(message = "End date cannot be null")
    @Future(message = "End date must be in the future")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_active")
    private Boolean isActive;
}
