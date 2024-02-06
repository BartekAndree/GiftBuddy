package com.giftbuddygroup.giftbuddy.service;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.giftbuddygroup.giftbuddy.common.exception.EmailAlreadyExistsException;
import com.giftbuddygroup.giftbuddy.common.exception.UserProfileNotFoundException;
import com.giftbuddygroup.giftbuddy.common.exception.UsernameAlreadyExistsException;
import com.giftbuddygroup.giftbuddy.model.dto.request.UserProfileRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.UserProfileResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;
import com.giftbuddygroup.giftbuddy.model.mapper.UserProfileMapper;
import com.giftbuddygroup.giftbuddy.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileResponseDTO createUser(UserProfileRequestDTO userProfileRequestDTO) {
        checkUsernameAvailability(userProfileRequestDTO.getUsername());
        checkEmailAvailability(userProfileRequestDTO.getEmail());
        UserProfile userProfile = UserProfileMapper.toUserProfile(userProfileRequestDTO);
        userProfile = userProfileRepository.save(userProfile);
        UserProfileResponseDTO response = UserProfileMapper.toUserProfileResponseDTO(userProfile);
        log.info("User created: {}", response.getId());
        return response;
    }

    public UserProfileResponseDTO getUserByExternalId(UUID externalId) {
        UserProfile userProfile =
                userProfileRepository
                        .findByExternalId(externalId)
                        .orElseThrow(() -> new UserProfileNotFoundException(externalId));
        return UserProfileMapper.toUserProfileResponseDTO(userProfile);
    }

    public void deleteUser(UUID id) {
        UserProfile userProfile = findUserOrThrow(id);
        userProfileRepository.deleteById(userProfile.getId());
        UserProfileResponseDTO response = UserProfileMapper.toUserProfileResponseDTO(userProfile);
        log.info("User deleted: {}", response.getId());
    }

    public boolean isUserWithExternalIdExist(UUID externalId) {
        return userProfileRepository.existsByExternalId(externalId);
    }

    private UserProfile findUserOrThrow(UUID id) {
        return userProfileRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(id.toString()));
    }

    private void checkUsernameAvailability(String username) {
        userProfileRepository
                .findByUsername(username)
                .ifPresent(
                        user -> {
                            throw new UsernameAlreadyExistsException(username);
                        });
    }

    private void checkEmailAvailability(String email) {
        userProfileRepository
                .findByEmail(email)
                .ifPresent(
                        user -> {
                            throw new EmailAlreadyExistsException(email);
                        });
    }
}
