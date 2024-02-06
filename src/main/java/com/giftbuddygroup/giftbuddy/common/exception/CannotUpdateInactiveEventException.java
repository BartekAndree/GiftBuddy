package com.giftbuddygroup.giftbuddy.common.exception;

import java.time.LocalDate;
import java.util.UUID;

public class CannotUpdateInactiveEventException extends RuntimeException {

    public CannotUpdateInactiveEventException(UUID eventId, LocalDate endDate) {
        super("Cannot update inactive event: " + eventId + " with end date: " + endDate);
    }
}
