package com.giftbuddygroup.giftbuddy.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.giftbuddygroup.giftbuddy.model.dto.request.UserProfileRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.UserProfileResponseDTO;
import com.giftbuddygroup.giftbuddy.service.UserProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/{externalId}")
    @PreAuthorize("@jwtService.authenticateUserByExternalId(#externalId)")
    public ResponseEntity<UserProfileResponseDTO> getUserDetailsByExternalId(
            @PathVariable UUID externalId) {
        UserProfileResponseDTO userProfileResponseDTO =
                userProfileService.getUserByExternalId(externalId);
        return new ResponseEntity<>(userProfileResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserProfileResponseDTO> createUser(
            @RequestBody @Valid UserProfileRequestDTO userProfileRequestDTO) {
        UserProfileResponseDTO userProfileResponseDTO =
                userProfileService.createUser(userProfileRequestDTO);
        return new ResponseEntity<>(userProfileResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("@jwtService.authenticateUserById(#userId)")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userProfileService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
