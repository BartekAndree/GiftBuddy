package com.giftbuddygroup.giftbuddy.controller;

import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.giftbuddygroup.giftbuddy.model.dto.request.EventRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.EventResponseDTO;
import com.giftbuddygroup.giftbuddy.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Set<EventResponseDTO>> getAllEvents() {
        Set<EventResponseDTO> eventsResponseDTOs = eventService.getAllEventsWhereUserParticipate();
        return new ResponseEntity<>(eventsResponseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    @PreAuthorize("@jwtService.isUserParticipantOfEvent(#eventId)")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable UUID eventId) {
        EventResponseDTO eventResponseDTO = eventService.getEventById(eventId);
        return new ResponseEntity<>(eventResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("@jwtService.isUserOrganizerOfEvent(#eventId)")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(
            @RequestBody @Valid EventRequestDTO eventRequestDTO) {
        EventResponseDTO eventResponseDTO = eventService.createEvent(eventRequestDTO);
        return new ResponseEntity<>(eventResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("@jwtService.isUserOrganizerOfEvent(#eventId)")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable UUID eventId, @RequestBody @Valid EventRequestDTO eventRequestDTO) {
        EventResponseDTO eventResponseDTO = eventService.updateEvent(eventId, eventRequestDTO);
        return new ResponseEntity<>(eventResponseDTO, HttpStatus.OK);
    }
}
