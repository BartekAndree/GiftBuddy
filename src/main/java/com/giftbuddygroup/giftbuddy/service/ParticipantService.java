package com.giftbuddygroup.giftbuddy.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.giftbuddygroup.giftbuddy.common.exception.EventNotFoundException;
import com.giftbuddygroup.giftbuddy.common.exception.ParticipantNotFoundException;
import com.giftbuddygroup.giftbuddy.common.exception.UserAlreadyInEventException;
import com.giftbuddygroup.giftbuddy.common.exception.UserProfileNotFoundException;
import com.giftbuddygroup.giftbuddy.model.dto.request.ParticipantRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.ParticipantDetailsResponseDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.ParticipantResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.Event;
import com.giftbuddygroup.giftbuddy.model.entity.Participant;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;
import com.giftbuddygroup.giftbuddy.model.factories.ParticipantRequestDTOFactory;
import com.giftbuddygroup.giftbuddy.model.mapper.ParticipantMapper;
import com.giftbuddygroup.giftbuddy.repository.EmailBufferRepository;
import com.giftbuddygroup.giftbuddy.repository.EventRepository;
import com.giftbuddygroup.giftbuddy.repository.ParticipantRepository;
import com.giftbuddygroup.giftbuddy.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final UserProfileRepository userProfileRepository;
    private final EmailBufferRepository emailBufferRepository;

    public ParticipantResponseDTO createParticipant(ParticipantRequestDTO participantRequestDTO) {
        Event event = findEventOrThrow(participantRequestDTO.getEventId());
        UserProfile user = findUserProfileOrThrow(participantRequestDTO.getUserId());
        userShouldNotExistInEvent(event.getId(), user.getId());
        Participant participant = ParticipantMapper.toParticipant(event, user);
        participant = participantRepository.save(participant);
        ParticipantResponseDTO response = ParticipantMapper.toParticipantResponseDTO(participant);
        log.info("Participant created: {}", response.getId());
        return response;
    }

    public void deleteParticipant(UUID participantId) {
        Participant participant = findParticipantOrThrow(participantId);
        participantRepository.delete(participant);
        log.info("Participant deleted: {}", participant.getId());
    }

    public void settleParticipants(List<UUID> participantIdList) {
        if (participantIdList == null || participantIdList.isEmpty()) {
            log.info("No participants to settle.");
            return;
        }
        Set<UUID> participantIds = new HashSet<>(participantIdList);
        for (UUID participantId : participantIds) {
            Participant participant = findParticipantOrThrow(participantId);

            if (participant.getIsSettled()) {
                log.info("Participant already settled: {}", participant.getId());
                return;
            }
            participant.setIsSettled(true);
            participantRepository.save(participant);
            log.info("Participant settled: {}", participant.getId());
            updateEventCurrentAmount(participant);
        }
    }

    private synchronized void updateEventCurrentAmount(Participant participant) {
        Event event = participant.getEvent();
        event.setCurrentAmount(event.getCurrentAmount().add(event.getContribution()));
        eventRepository.save(event);
        log.info("Event currentAmount updated: {}", event.getId());
    }

    public List<ParticipantDetailsResponseDTO> getParticipantsByEventId(UUID eventId) {
        List<ParticipantDetailsResponseDTO> response = new ArrayList<>();
        Event event = findEventOrThrow(eventId);
        List<Participant> participants = participantRepository.findByEventId(event.getId());
        participants.forEach(
                participant -> {
                    ParticipantDetailsResponseDTO participantDetailsResponseDTO =
                            ParticipantMapper.toParticipantDetailsResponseDTO(participant);
                    response.add(participantDetailsResponseDTO);
                });
        log.info("Participants found: {}", response.size());
        return response;
    }

    public void createParticipantsFromEmailBuffer(String email) {
        List<UUID> eventIds = emailBufferRepository.findEventIdsByEmail(email);
        if (!eventIds.isEmpty()) {
            Optional<UUID> userIdOptional = userProfileRepository.findIdByEmail(email);

            if (userIdOptional.isPresent()) {
                UUID userId = userIdOptional.get();

                for (UUID eventId : eventIds) {
                    ParticipantRequestDTO participantRequestDTO =
                            ParticipantRequestDTOFactory.buildParticipantRequestDTO(
                                    eventId, userId);
                    createParticipant(participantRequestDTO);
                }

                emailBufferRepository.deleteByEmail(email);
            }
        }
    }

    private Event findEventOrThrow(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    private UserProfile findUserProfileOrThrow(UUID id) {
        return userProfileRepository
                .findById(id)
                .orElseThrow(() -> new UserProfileNotFoundException(id));
    }

    private void userShouldNotExistInEvent(UUID eventId, UUID userId) {
        if (Boolean.TRUE.equals(participantRepository.existsByEventIdAndUserId(eventId, userId))) {
            throw new UserAlreadyInEventException(userId, eventId);
        }
    }

    private Participant findParticipantOrThrow(UUID participantId) {
        return participantRepository
                .findById(participantId)
                .orElseThrow(() -> new ParticipantNotFoundException(participantId));
    }
}
