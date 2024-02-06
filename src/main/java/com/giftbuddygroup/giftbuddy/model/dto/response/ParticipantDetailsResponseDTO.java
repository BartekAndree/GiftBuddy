package com.giftbuddygroup.giftbuddy.model.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDetailsResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

    @JsonProperty("isSettled")
    private boolean isSettled;
}
