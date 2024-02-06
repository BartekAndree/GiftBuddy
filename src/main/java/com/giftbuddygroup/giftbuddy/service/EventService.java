package com.giftbuddygroup.giftbuddy.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Service;

import com.giftbuddygroup.giftbuddy.common.exception.CannotUpdateInactiveEventException;
import com.giftbuddygroup.giftbuddy.common.exception.EventNotFoundException;
import com.giftbuddygroup.giftbuddy.model.dto.request.EventRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.request.ParticipantRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.EventResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.Event;
import com.giftbuddygroup.giftbuddy.model.entity.Participant;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;
import com.giftbuddygroup.giftbuddy.model.factories.ParticipantRequestDTOFactory;
import com.giftbuddygroup.giftbuddy.model.mapper.EventMapper;
import com.giftbuddygroup.giftbuddy.repository.EmailBufferRepository;
import com.giftbuddygroup.giftbuddy.repository.EventRepository;
import com.giftbuddygroup.giftbuddy.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final EmailBufferRepository emailBufferRepository;
    private final JwtService jwtService;
    private final ParticipantService participantService;

    public EventResponseDTO getEventById(UUID eventId) {
        Event event = findEventOrThrow(eventId);
        return EventMapper.toEventResponseDTO(event);
    }

    public Set<EventResponseDTO> getAllEventsWhereUserParticipate() {
        UserProfile organizer = jwtService.findUserProfileByExternalId();
        Set<Event> eventSet = new HashSet<>();
        List<Participant> participantList = participantRepository.findByUserId(organizer.getId());
        for (Participant participant : participantList) {
            eventSet.add(participant.getEvent());
        }
        return EventMapper.toEventResponseDTOSet(eventSet);
    }

    public void deleteEvent(UUID eventId) {
        Event event = findEventOrThrow(eventId);
        if (!event.getIsActive()) {
            throw new CannotUpdateInactiveEventException(eventId, event.getEndDate());
        }
        eventRepository.deleteById(event.getId());
        emailBufferRepository.deleteByEventId(eventId);
        log.info("Event deleted: {}", eventId);
    }

    public EventResponseDTO createEvent(EventRequestDTO eventRequestDTO) {
        UserProfile organizer = jwtService.findUserProfileByExternalId();
        Event event = EventMapper.toEvent(eventRequestDTO);
        event.setOrganizer(organizer);
        event.setCurrentAmount(BigDecimal.ZERO);
        event.setIsActive(true);
        event = eventRepository.save(event);
        EventResponseDTO response = EventMapper.toEventResponseDTO(event);
        log.info("Event created: {}", response.getId());
        ParticipantRequestDTO participant =
                ParticipantRequestDTOFactory.buildParticipantRequestDTO(
                        response.getId(), response.getOrganizerId());
        participantService.createParticipant(participant);
        return response;
    }

    public EventResponseDTO updateEvent(UUID eventId, EventRequestDTO eventRequestDTO) {
        Event event = findEventOrThrow(eventId);
        if (!event.getIsActive()) {
            throw new CannotUpdateInactiveEventException(eventId, event.getEndDate());
        }
        EventMapper.updateEventFromEventRequestDTO(event, eventRequestDTO);
        event = eventRepository.save(event);
        EventResponseDTO response = EventMapper.toEventResponseDTO(event);
        log.info("Event updated: {}", response.getId());
        return response;
    }

    public void checkAndUpdateEventStatus() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<Event> events = eventRepository.findAllByEndDateAndIsActive(yesterday, true);

        for (Event event : events) {
            event.setIsActive(false);
            eventRepository.save(event);
        }
    }

    private Event findEventOrThrow(UUID eventId) {
        return eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
    }
}
