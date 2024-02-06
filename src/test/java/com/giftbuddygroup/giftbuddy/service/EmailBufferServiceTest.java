package com.giftbuddygroup.giftbuddy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.giftbuddygroup.giftbuddy.model.dto.response.EmailBufferResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.EmailBuffer;
import com.giftbuddygroup.giftbuddy.model.entity.Event;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;
import com.giftbuddygroup.giftbuddy.repository.EmailBufferRepository;
import com.giftbuddygroup.giftbuddy.repository.UserProfileRepository;

@ExtendWith(MockitoExtension.class)
class EmailBufferServiceTest {

    @Mock private EmailBufferRepository emailBufferRepository;

    @Mock private UserProfileRepository userProfileRepository;

    @InjectMocks private EmailBufferService emailBufferService;

    @Test
    void getEmailsForEvent() {

        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);

        UUID organizerId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(organizerId);
        event.setOrganizer(organizer);

        when(emailBufferRepository.findAllByEventId(eventId))
                .thenReturn(List.of(new EmailBuffer(eventId, "test@test.com")));

        List<EmailBufferResponseDTO> result = emailBufferService.getEmailsForEvent(eventId);

        assertEquals(1, result.size());
        verify(emailBufferRepository, times(1)).findAllByEventId(eventId);

        System.out.println("Email buffers for the event:");
        for (EmailBufferResponseDTO emailBufferResponseDTO : result) {
            System.out.println(emailBufferResponseDTO);
        }
    }

    @Test
    void saveEmailOrCreateParticipant_NewEmail() {

        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);

        UUID organizerId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(organizerId);
        event.setOrganizer(organizer);

        String newEmail = "testing@test.com";

        when(userProfileRepository.findIdByEmail(eq(newEmail))).thenReturn(Optional.empty());
        when(emailBufferRepository.existsByEmailAndEventId(eq(newEmail), eq(eventId)))
                .thenReturn(false);

        emailBufferService.saveEmailOrCreateParticipant(newEmail, eventId);

        verify(userProfileRepository, times(1)).findIdByEmail(eq(newEmail));
        verify(emailBufferRepository, times(1)).save(Mockito.any());
    }

    @Test
    void saveEmailOrCreateParticipant_EmailExistInBuffer() {

        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);

        UUID organizerId = UUID.randomUUID();
        UserProfile organizer = new UserProfile();
        organizer.setId(organizerId);
        event.setOrganizer(organizer);

        String existingEmail = "testing@test.com";

        when(userProfileRepository.findIdByEmail(eq(existingEmail))).thenReturn(Optional.empty());
        when(emailBufferRepository.existsByEmailAndEventId(eq(existingEmail), eq(eventId)))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> emailBufferService.saveEmailOrCreateParticipant(existingEmail, eventId));
        verify(userProfileRepository, times(1)).findIdByEmail(eq(existingEmail));
        verify(emailBufferRepository, never()).save(Mockito.any());
    }

    @Test
    void deleteByEmailBufferId() {

        UUID emailBufferId = UUID.randomUUID();

        emailBufferService.deleteByEmailBufferId(emailBufferId);

        verify(emailBufferRepository, times(1)).deleteById(eq((emailBufferId)));
    }

    @Test
    void deleteEmailBufferByEmail() {

        String emailBufferEmail = "testing@test.com";

        emailBufferService.deleteEmailBufferByEmail(emailBufferEmail);

        verify(emailBufferRepository, times(1)).deleteByEmail(emailBufferEmail);
    }
}
