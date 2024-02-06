package com.giftbuddygroup.giftbuddy.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.giftbuddygroup.giftbuddy.model.dto.request.EmailRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.EmailBufferResponseDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.SaveEmailResponseDTO;
import com.giftbuddygroup.giftbuddy.service.EmailBufferService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/emailbuffer")
public class EmailBufferController {

    private final EmailBufferService emailBufferService;

    @GetMapping("/{eventId}")
    @PreAuthorize("@jwtService.isUserOrganizerOfEvent(#eventId)")
    public ResponseEntity<List<EmailBufferResponseDTO>> getEmailBufferByEventId(
            @PathVariable UUID eventId) {
        List<EmailBufferResponseDTO> response = emailBufferService.getEmailsForEvent(eventId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{eventId}")
    @PreAuthorize("@jwtService.isUserOrganizerOfEvent(#eventId)")
    public ResponseEntity<SaveEmailResponseDTO> saveEmailToBuffer(
            @PathVariable UUID eventId, @RequestBody @Valid EmailRequestDTO emailRequestDTO) {
        SaveEmailResponseDTO response =
                emailBufferService.saveEmailOrCreateParticipant(
                        emailRequestDTO.getEmail(), eventId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{eventId}/{emailBufferId}")
    @PreAuthorize("@jwtService.isUserOrganizerOfEvent(#eventId)")
    public ResponseEntity<EmailBufferResponseDTO> deleteEmailsForEventAndEmail(
            @PathVariable UUID eventId, @PathVariable UUID emailBufferId) {
        emailBufferService.deleteByEmailBufferId(emailBufferId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
