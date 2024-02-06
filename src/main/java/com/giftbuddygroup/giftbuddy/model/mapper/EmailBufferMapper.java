package com.giftbuddygroup.giftbuddy.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.giftbuddygroup.giftbuddy.model.dto.response.EmailBufferResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.EmailBuffer;

public class EmailBufferMapper {

    public static List<EmailBufferResponseDTO> toEmailBufferResponseDTOList(
            List<EmailBuffer> emailBuffers) {
        return emailBuffers.stream()
                .map(EmailBufferMapper::toEmailBufferResponseDTO)
                .collect(Collectors.toList());
    }

    public static EmailBufferResponseDTO toEmailBufferResponseDTO(EmailBuffer emailBuffer) {
        return EmailBufferResponseDTO.builder()
                .id(emailBuffer.getId())
                .eventId(emailBuffer.getEventId())
                .email(emailBuffer.getEmail())
                .build();
    }
}
