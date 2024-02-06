package com.giftbuddygroup.giftbuddy.model.mapper;

import java.util.ArrayList;
import java.util.List;

import com.giftbuddygroup.giftbuddy.model.dto.request.UserProfileRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.UserProfileResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;

public class UserProfileMapper {
    public static UserProfileResponseDTO toUserProfileResponseDTO(UserProfile userProfile) {
        return UserProfileResponseDTO.builder()
                .id(userProfile.getId())
                .username(userProfile.getUsername())
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .email(userProfile.getEmail())
                .phoneNumber(userProfile.getPhoneNumber())
                .externalId(userProfile.getExternalId())
                .build();
    }

    public static UserProfile toUserProfile(UserProfileRequestDTO userProfileRequestDTO) {
        return UserProfile.builder()
                .username(userProfileRequestDTO.getUsername())
                .firstName(userProfileRequestDTO.getFirstName())
                .lastName(userProfileRequestDTO.getLastName())
                .email(userProfileRequestDTO.getEmail())
                .phoneNumber(userProfileRequestDTO.getPhoneNumber())
                .externalId(userProfileRequestDTO.getExternalId())
                .build();
    }

    public static List<UserProfileResponseDTO> toUserProfileResponseDTOs(
            List<UserProfile> userProfiles) {
        List<UserProfileResponseDTO> UserProfileResponseDTOs = new ArrayList<>();
        for (UserProfile userProfile : userProfiles) {
            UserProfileResponseDTOs.add(toUserProfileResponseDTO(userProfile));
        }
        return UserProfileResponseDTOs;
    }
}
