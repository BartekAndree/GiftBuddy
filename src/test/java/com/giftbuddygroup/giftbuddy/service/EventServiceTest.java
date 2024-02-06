package com.giftbuddygroup.giftbuddy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.giftbuddygroup.giftbuddy.common.exception.CannotUpdateInactiveEventException;
import com.giftbuddygroup.giftbuddy.common.exception.EventNotFoundException;
import com.giftbuddygroup.giftbuddy.model.dto.request.EventRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.request.ParticipantRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.EventResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.Event;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;
import com.giftbuddygroup.giftbuddy.model.mapper.EventMapper;
import com.giftbuddygroup.giftbuddy.repository.EmailBufferRepository;
import com.giftbuddygroup.giftbuddy.repository.EventRepository;
import com.giftbuddygroup.giftbuddy.repository.ParticipantRepository;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock private EventRepository eventRepository;

    @Mock private EmailBufferRepository emailBufferRepository;

    @Mock private JwtService jwtService;

    @Mock private ParticipantRepository participantRepository;

    @Mock private ParticipantService participantService;

    @InjectMocks private EventService eventService;

    @Test
    void getEventById() {

        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);

        UUID organizerId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(organizerId);
        event.setOrganizer(organizer);

        EventResponseDTO expectedEventResponseDTO = EventMapper.toEventResponseDTO(event);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        EventResponseDTO resultEventResponseDTO = eventService.getEventById(eventId);

        assertEquals(expectedEventResponseDTO, resultEventResponseDTO);
        verify(eventRepository, times(1)).findById(eventId);

        System.out.println(event);
    }

    @Test
    void getEventById_EventNotFound() {

        UUID eventId = UUID.randomUUID();

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.getEventById(eventId));

        verify(eventRepository, times(1)).findById(eventId);

        System.out.println("Event with ID " + eventId + " not found");
    }

    @Test
    void getAllEventsWhereUserParticipate_EmptySet() {

        UUID userId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(userId);

        when(jwtService.findUserProfileByExternalId()).thenReturn(organizer);

        when(participantRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        eventService.getAllEventsWhereUserParticipate();

        verify(jwtService, times(1)).findUserProfileByExternalId();
        verify(participantRepository, times(1)).findByUserId(userId);

        System.out.println("Participant set is empty");
    }

    @Test
    void deleteEvent_inactiveEvent() {

        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);
        event.setIsActive(false);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        assertThrows(
                CannotUpdateInactiveEventException.class, () -> eventService.deleteEvent(eventId));

        verify(eventRepository, times(1)).findById(eventId);

        System.out.println("Cannot delete inactive event with ID: " + eventId);
    }

    @Test
    void deleteEvent_activeEvent() {

        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);
        event.setIsActive(true);

        UUID organizerId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(organizerId);
        event.setOrganizer(organizer);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        assertDoesNotThrow(() -> eventService.deleteEvent(eventId));

        verify(eventRepository, times(1)).findById(eventId);
        verify(eventRepository, times(1)).deleteById(eventId);
        verify(emailBufferRepository, times(1)).deleteByEventId(eventId);

        System.out.println("Deleted active event with ID: " + eventId);
    }

    @Test
    void createEvent() {

        UUID organizerId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(organizerId);

        EventRequestDTO eventRequestDTO = new EventRequestDTO();

        Event event = EventMapper.toEvent(eventRequestDTO);

        UUID eventId = UUID.randomUUID();
        event.setId(eventId);
        event.setOrganizer(organizer);
        event.setTitle("Sample Event");
        event.setIsActive(true);

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        EventResponseDTO resultEventResponseDTO = eventService.createEvent(eventRequestDTO);

        assertNotNull(resultEventResponseDTO);
        assertEquals(organizerId, resultEventResponseDTO.getOrganizerId());
        assertEquals("Sample Event", resultEventResponseDTO.getTitle());
        assertTrue(resultEventResponseDTO.getIsActive());

        verify(jwtService, times(1)).findUserProfileByExternalId();
        verify(eventRepository, times(1)).save(any(Event.class));
        verify(participantService, times(1)).createParticipant(any(ParticipantRequestDTO.class));
    }

    @Test
    void updateEvent_ActiveEvent() {

        UUID eventId = UUID.randomUUID();
        EventRequestDTO eventRequestDTO = new EventRequestDTO();
        Event event = new Event();
        event.setId(eventId);

        UUID organizerId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(organizerId);
        event.setOrganizer(organizer);

        event.setIsActive(true);

        when(eventRepository.findById(eventId)).thenReturn(java.util.Optional.of(event));
        when(eventRepository.save(any(Event.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        EventResponseDTO result = eventService.updateEvent(eventId, eventRequestDTO);

        assertNotNull(result);
        assertEquals(eventId, result.getId());
        assertTrue(result.getIsActive());
        verify(eventRepository, times(1)).findById(eventId);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void updateEvent_InactiveEvent() {

        UUID eventId = UUID.randomUUID();
        EventRequestDTO eventRequestDTO = new EventRequestDTO();
        Event event = new Event();
        event.setId(eventId);

        UUID organizerId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(organizerId);
        event.setOrganizer(organizer);

        event.setIsActive(false);

        when(eventRepository.findById(eventId)).thenReturn(java.util.Optional.of(event));

        assertThrows(
                CannotUpdateInactiveEventException.class,
                () -> eventService.updateEvent(eventId, eventRequestDTO));

        verify(eventRepository, times(1)).findById(eventId);
        verify(eventRepository, never()).save(any(Event.class));
    }
}
