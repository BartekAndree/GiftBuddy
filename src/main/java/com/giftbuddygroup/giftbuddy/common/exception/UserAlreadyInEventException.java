package com.giftbuddygroup.giftbuddy.common.exception;

import java.util.UUID;

public class UserAlreadyInEventException extends RuntimeException {
    public UserAlreadyInEventException(UUID userId, UUID eventId) {
        super("User with id: " + userId + " already exists in event with id: " + eventId);
    }
}
