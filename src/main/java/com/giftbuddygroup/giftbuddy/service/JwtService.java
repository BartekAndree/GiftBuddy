package com.giftbuddygroup.giftbuddy.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.giftbuddygroup.giftbuddy.common.exception.UserNotParticipatingInEventException;
import com.giftbuddygroup.giftbuddy.common.exception.UserProfileNotFoundException;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;
import com.giftbuddygroup.giftbuddy.repository.EventRepository;
import com.giftbuddygroup.giftbuddy.repository.ParticipantRepository;
import com.giftbuddygroup.giftbuddy.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    private final EventRepository eventRepository;
    private final UserProfileRepository userProfileRepository;
    private final ParticipantRepository participantRepository;

    public boolean isUserParticipantOfEvent(UUID eventId) {
        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userExternalId = authentication.getTokenAttributes().get("sub").toString();

        Optional<UserProfile> userProfileOpt =
                userProfileRepository.findByExternalId(UUID.fromString(userExternalId));
        if (userProfileOpt.isPresent()) {
            UUID userId = userProfileOpt.get().getId();
            if (participantRepository.existsByEventIdAndUserId(eventId, userId)) {
                log.info("User is a participant of event: {}", eventId);
                return true;
            } else {
                log.info("User is not a participant of event: {}", eventId);
                throw new UserNotParticipatingInEventException(eventId, userId);
            }
        } else {
            log.info("User profile not found for external id: {}", userExternalId);
            throw new UserProfileNotFoundException(UUID.fromString(userExternalId));
        }
    }

    public boolean isUserOrganizerOfEvent(UUID eventId) {
        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userExternalId = authentication.getTokenAttributes().get("sub").toString();

        Optional<UserProfile> userProfileOpt =
                userProfileRepository.findByExternalId(UUID.fromString(userExternalId));
        if (userProfileOpt.isPresent()) {
            UUID userId = userProfileOpt.get().getId();
            if (eventRepository.existsByIdAndOrganizerId(eventId, userId)) {
                log.info("User is the organizer of event: {}", eventId);
                return true;
            } else {
                log.info("User is not the organizer of event: {}", eventId);
                throw new UserNotParticipatingInEventException(eventId, userId);
            }
        } else {
            log.info("User profile not found for external id: {}", userExternalId);
            throw new UserProfileNotFoundException(UUID.fromString(userExternalId));
        }
    }

    public boolean authenticateUserByExternalId(UUID externalId) {
        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userExternalId = authentication.getTokenAttributes().get("sub").toString();
        return externalId.equals(UUID.fromString(userExternalId));
    }

    public boolean authenticateUserById(UUID userId) {
        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userExternalId = authentication.getTokenAttributes().get("sub").toString();
        Optional<UserProfile> userProfileOpt =
                userProfileRepository.findByExternalId(UUID.fromString(userExternalId));
        if (userProfileOpt.isPresent()) {
            return userId.equals(userProfileOpt.get().getId());
        } else {
            log.info("User profile not found for external id: {}", userExternalId);
            throw new UserProfileNotFoundException(UUID.fromString(userExternalId));
        }
    }

    public UserProfile findUserProfileByExternalId() {
        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userExternalId = authentication.getTokenAttributes().get("sub").toString();
        UUID externalIdUuid = UUID.fromString(userExternalId);

        Optional<UserProfile> userProfileOpt =
                userProfileRepository.findByExternalId(externalIdUuid);
        if (userProfileOpt.isPresent()) {
            return userProfileOpt.get();
        } else {
            log.info("User profile not found for external id: {}", userExternalId);
            throw new UserProfileNotFoundException(externalIdUuid);
        }
    }
}
