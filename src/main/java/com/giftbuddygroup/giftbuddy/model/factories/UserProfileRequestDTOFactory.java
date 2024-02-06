package com.giftbuddygroup.giftbuddy.model.factories;

import java.util.UUID;

import org.springframework.security.oauth2.jwt.Jwt;

import com.giftbuddygroup.giftbuddy.model.dto.request.UserProfileRequestDTO;

public class UserProfileRequestDTOFactory {

    public static UserProfileRequestDTO buildUserProfileRequestDTO(Jwt jwt) {
        return UserProfileRequestDTO.builder()
                .username(jwt.getClaim("preferred_username"))
                .firstName(jwt.getClaim("given_name"))
                .lastName(jwt.getClaim("family_name"))
                .email(jwt.getClaim("email"))
                .externalId(UUID.fromString(jwt.getClaim("sub")))
                .build();
    }
}
