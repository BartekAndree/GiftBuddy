package com.giftbuddygroup.giftbuddy.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.giftbuddygroup.giftbuddy.model.dto.request.ParticipantRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.EmailBufferResponseDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.ParticipantResponseDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.SaveEmailResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.EmailBuffer;
import com.giftbuddygroup.giftbuddy.model.factories.ParticipantRequestDTOFactory;
import com.giftbuddygroup.giftbuddy.model.mapper.EmailBufferMapper;
import com.giftbuddygroup.giftbuddy.repository.EmailBufferRepository;
import com.giftbuddygroup.giftbuddy.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailBufferService {

    private final EmailBufferRepository emailBufferRepository;
    private final UserProfileRepository userProfileRepository;
    private final ParticipantService participantService;

    public List<EmailBufferResponseDTO> getEmailsForEvent(UUID eventId) {
        List<EmailBuffer> emailBuffers = emailBufferRepository.findAllByEventId(eventId);
        return EmailBufferMapper.toEmailBufferResponseDTOList(emailBuffers);
    }

    public SaveEmailResponseDTO saveEmailOrCreateParticipant(String email, UUID eventId) {
        Optional<UUID> userIdOptional = userProfileRepository.findIdByEmail(email);
        if (userIdOptional.isPresent()) {
            ParticipantRequestDTO participantRequestDTO =
                    ParticipantRequestDTOFactory.buildParticipantRequestDTO(
                            eventId, userIdOptional.get());
            ParticipantResponseDTO participantResponse =
                    participantService.createParticipant(participantRequestDTO);
            return new SaveEmailResponseDTO(null, participantResponse);
        } else {
            EmailBufferResponseDTO emailBufferResponse = saveEmailToBuffer(email, eventId);
            return new SaveEmailResponseDTO(emailBufferResponse, null);
        }
    }

    private EmailBufferResponseDTO saveEmailToBuffer(String email, UUID eventId) {
        if (emailBufferRepository.existsByEmailAndEventId(email, eventId)) {
            log.info("Email {} already exists in buffer for event: {}", email, eventId);
            throw new IllegalArgumentException("Email already exists in buffer for event");
        }
        EmailBuffer emailBuffer = new EmailBuffer();
        emailBuffer.setEventId(eventId);
        emailBuffer.setEmail(email);
        emailBufferRepository.save(emailBuffer);
        log.info("Email saved to buffer: {}", emailBuffer);
        return EmailBufferMapper.toEmailBufferResponseDTO(emailBuffer);
    }

    public void deleteByEmailBufferId(UUID emailBufferId) {
        emailBufferRepository.deleteById(emailBufferId);
        log.info("Email buffer deleted: {}", emailBufferId);
    }

    public void deleteEmailBufferByEmail(String email) {
        emailBufferRepository.deleteByEmail(email);
        log.info("Email buffer deleted: {}", email);
    }
}
