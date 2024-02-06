package com.giftbuddygroup.giftbuddy.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.giftbuddygroup.giftbuddy.model.dto.request.UserProfileRequestDTO;
import com.giftbuddygroup.giftbuddy.model.factories.UserProfileRequestDTOFactory;
import com.giftbuddygroup.giftbuddy.service.EmailBufferService;
import com.giftbuddygroup.giftbuddy.service.ParticipantService;
import com.giftbuddygroup.giftbuddy.service.UserProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserProfileService userProfileService;
    private final ParticipantService participantService;
    private final EmailBufferService emailBufferService;

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        Collection<GrantedAuthority> authorities = extractRoles(source);

        if (!isUserAlreadyExist(source)) {
            log.info("User does not exist, creating new user");
            createUserFromJwtTokenInformation(source);
        }

        return new JwtAuthenticationToken(source, authorities);
    }

    private Collection<GrantedAuthority> extractRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) {
            return Collections.emptySet();
        }

        Map<String, Object> resource = (Map<String, Object>) resourceAccess.get("frontend");
        if (resource == null) {
            return Collections.emptySet();
        }

        Collection<String> roles = (Collection<String>) resource.get("roles");
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

    private void createUserFromJwtTokenInformation(Jwt jwt) {
        UserProfileRequestDTO userProfileRequestDTO =
                UserProfileRequestDTOFactory.buildUserProfileRequestDTO(jwt);
        userProfileService.createUser(userProfileRequestDTO);
        participantService.createParticipantsFromEmailBuffer(userProfileRequestDTO.getEmail());
        emailBufferService.deleteEmailBufferByEmail(userProfileRequestDTO.getEmail());
    }

    private boolean isUserAlreadyExist(Jwt jwt) {
        UUID userExternalId = UUID.fromString(jwt.getClaim("sub"));
        return userProfileService.isUserWithExternalIdExist(userExternalId);
    }
}
