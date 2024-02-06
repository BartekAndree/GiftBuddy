package com.giftbuddygroup.giftbuddy.model.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailBufferResponseDTO {
    UUID id;
    UUID eventId;
    String email;
}
