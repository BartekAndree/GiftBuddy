package com.giftbuddygroup.giftbuddy.model.dto.request;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRequestDTO {

    @NotNull(message = "Event id cannot be blank")
    private UUID eventId;

    @NotNull(message = "User id cannot be blank")
    private UUID userId;
}
