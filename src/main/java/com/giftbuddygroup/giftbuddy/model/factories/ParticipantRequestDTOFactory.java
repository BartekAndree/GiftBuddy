package com.giftbuddygroup.giftbuddy.model.factories;

import java.util.UUID;

import com.giftbuddygroup.giftbuddy.model.dto.request.ParticipantRequestDTO;

public class ParticipantRequestDTOFactory {
    public static ParticipantRequestDTO buildParticipantRequestDTO(UUID eventId, UUID userId) {
        return ParticipantRequestDTO.builder().eventId(eventId).userId(userId).build();
    }
}
