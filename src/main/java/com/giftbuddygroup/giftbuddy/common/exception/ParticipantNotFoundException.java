package com.giftbuddygroup.giftbuddy.common.exception;

import java.util.UUID;

public class ParticipantNotFoundException extends RuntimeException {
    public ParticipantNotFoundException(UUID participantId) {
        super("Participant not found with id: " + participantId);
    }
}
