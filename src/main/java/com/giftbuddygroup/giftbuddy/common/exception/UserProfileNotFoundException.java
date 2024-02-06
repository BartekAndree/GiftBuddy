package com.giftbuddygroup.giftbuddy.common.exception;

import java.util.UUID;

public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException(UUID id) {
        super("User not found with id: " + id);
    }
}
