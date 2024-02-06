package com.giftbuddygroup.giftbuddy.common.exception;

import java.util.UUID;

public class UserNotParticipatingInEventException extends RuntimeException {

    public UserNotParticipatingInEventException(UUID eventId, UUID userId) {
        super(
                String.format(
                        "User with id %s is not participating in event with id %s",
                        userId, eventId));
    }
}
