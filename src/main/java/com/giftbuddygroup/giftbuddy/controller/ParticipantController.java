package com.giftbuddygroup.giftbuddy.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.giftbuddygroup.giftbuddy.model.dto.response.ParticipantDetailsResponseDTO;
import com.giftbuddygroup.giftbuddy.service.ParticipantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable UUID participantId) {
        participantService.deleteParticipant(participantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{eventId}")
    @PreAuthorize("@jwtService.isUserParticipantOfEvent(#eventId)")
    public ResponseEntity<List<ParticipantDetailsResponseDTO>> getParticipantsByEventId(
            @PathVariable UUID eventId) {
        List<ParticipantDetailsResponseDTO> participantDetailsResponseDTO =
                participantService.getParticipantsByEventId(eventId);
        return new ResponseEntity<>(participantDetailsResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/settle/{eventId}")
    @PreAuthorize("@jwtService.isUserOrganizerOfEvent(#eventId)")
    public ResponseEntity<Void> settleParticipant(
            @PathVariable String eventId, @RequestBody List<UUID> participantIdsList) {
        participantService.settleParticipants(participantIdsList);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
