package com.giftbuddygroup.giftbuddy.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveEmailResponseDTO {
    private EmailBufferResponseDTO emailBufferResponseDTO;
    private ParticipantResponseDTO participantResponseDTO;
}
