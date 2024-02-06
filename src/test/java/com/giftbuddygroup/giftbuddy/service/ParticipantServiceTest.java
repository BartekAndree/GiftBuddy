package com.giftbuddygroup.giftbuddy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.giftbuddygroup.giftbuddy.model.dto.request.ParticipantRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.ParticipantResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.Event;
import com.giftbuddygroup.giftbuddy.model.entity.Participant;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;
import com.giftbuddygroup.giftbuddy.repository.EventRepository;
import com.giftbuddygroup.giftbuddy.repository.ParticipantRepository;
import com.giftbuddygroup.giftbuddy.repository.UserProfileRepository;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @InjectMocks private ParticipantService participantService;

    @Mock private EventRepository eventRepository;

    @Mock private UserProfileRepository userProfileRepository;

    @Mock private ParticipantRepository participantRepository;

    @Test
    void createParticipant() {

        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);
        event.setIsActive(true);

        UUID organizerId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(organizerId);
        event.setOrganizer(organizer);

        UUID userId = UUID.randomUUID();
        UserProfile user = new UserProfile();
        user.setId(userId);

        ParticipantRequestDTO participantRequestDTO = new ParticipantRequestDTO(eventId, userId);

        when(eventRepository.findById(eq(eventId))).thenReturn(Optional.of(event));
        when(userProfileRepository.findById(eq(userId))).thenReturn(Optional.of(user));
        when(participantRepository.save(any()))
                .thenAnswer(
                        invocation -> {
                            Participant savedParticipant = invocation.getArgument(0);
                            savedParticipant.setId(UUID.randomUUID());
                            return savedParticipant;
                        });

        ParticipantResponseDTO result = participantService.createParticipant(participantRequestDTO);

        verify(eventRepository, times(1)).findById(eq(eventId));
        verify(userProfileRepository, times(1)).findById(eq(userId));
        verify(participantRepository, times(1)).save(any());

        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    void deleteParticipant() {

        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);

        UUID organizerId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(organizerId);
        event.setOrganizer(organizer);

        Participant participant = new Participant();
        UUID participantId = UUID.randomUUID();
        participant.setId(participantId);
        participant.setEvent(event);

        when(participantRepository.findById(eq(participantId)))
                .thenReturn(java.util.Optional.of(participant));

        participantService.deleteParticipant(participantId);

        verify(participantRepository, times(1)).findById(eq(participantId));
        verify(participantRepository, times(1)).delete(eq(participant));
    }

    @Test
    void settleParticipants_NoParticipants() {

        participantService.settleParticipants(Collections.emptyList());

        verify(participantRepository, never()).findById(any());
        verify(participantRepository, never()).save(any());
    }

    @Test
    void settleParticipants_ParticipantAlreadySettled() {

        UUID participantId = UUID.randomUUID();
        List<UUID> participantIdList = Collections.singletonList(participantId);
        Participant settledParticipant = new Participant();
        settledParticipant.setId(participantId);
        settledParticipant.setIsSettled(true);

        when(participantRepository.findById(participantId))
                .thenReturn(Optional.of(settledParticipant));

        participantService.settleParticipants(participantIdList);

        verify(participantRepository, times(1)).findById(participantId);
        verify(participantRepository, never()).save(any());
    }

    @Test
    void settleParticipants_ParticipantNotSettled() {

        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);
        event.setIsActive(true);
        event.setContribution(BigDecimal.valueOf(10.00));
        event.setCurrentAmount(BigDecimal.valueOf(0.0));

        UUID organizerId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(organizerId);
        event.setOrganizer(organizer);

        UUID participantId = UUID.randomUUID();
        List<UUID> participantIdList = Collections.singletonList(participantId);
        Participant unsettledParticipant = new Participant();
        unsettledParticipant.setId(participantId);
        unsettledParticipant.setIsSettled(false);
        unsettledParticipant.setEvent(event);

        when(participantRepository.findById(participantId))
                .thenReturn(Optional.of(unsettledParticipant));

        participantService.settleParticipants(participantIdList);

        verify(participantRepository, times(1)).findById(participantId);
        verify(participantRepository, times(1)).save(unsettledParticipant);
    }

    @Test
    void getParticipantsByEventId() {

        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);

        UserProfile userProfile = new UserProfile();
        userProfile.setId(UUID.randomUUID());

        Participant participant = new Participant();
        participant.setId(UUID.randomUUID());
        participant.setEvent(event);
        participant.setUser(userProfile);

        List<Participant> participants = Collections.singletonList(participant);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(participantRepository.findByEventId(eventId)).thenReturn(participants);

        participantService.getParticipantsByEventId(eventId);

        verify(eventRepository, times(1)).findById(eventId);
        verify(participantRepository, times(1)).findByEventId(eventId);
    }
}
