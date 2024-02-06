package com.giftbuddygroup.giftbuddy.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.giftbuddygroup.giftbuddy.model.dto.response.ParticipantDetailsResponseDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.ParticipantResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.Event;
import com.giftbuddygroup.giftbuddy.model.entity.Participant;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;

public class ParticipantMapper {

    public static ParticipantResponseDTO toParticipantResponseDTO(Participant participant) {
        return ParticipantResponseDTO.builder()
                .id(participant.getId())
                .eventId(participant.getEvent().getId())
                .userId(participant.getUser().getId())
                .isSettled(participant.getIsSettled())
                .build();
    }

    public static List<ParticipantResponseDTO> toParticipantResponseDTOs(
            List<Participant> participants) {
        return participants.stream()
                .map(ParticipantMapper::toParticipantResponseDTO)
                .collect(Collectors.toList());
    }

    public static Participant toParticipant(Event event, UserProfile user) {
        return Participant.builder().event(event).user(user).isSettled(false).build();
    }

    public static ParticipantDetailsResponseDTO toParticipantDetailsResponseDTO(
            Participant participant) {
        UserProfile user = participant.getUser();
        return ParticipantDetailsResponseDTO.builder()
                .id(participant.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .isSettled(participant.getIsSettled())
                .build();
    }
}
