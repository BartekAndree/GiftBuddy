package com.giftbuddygroup.giftbuddy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.giftbuddygroup.giftbuddy.model.entity.EmailBuffer;

public interface EmailBufferRepository extends JpaRepository<EmailBuffer, UUID> {

    @Query("SELECT e.eventId FROM EmailBuffer e WHERE e.email = :email")
    List<UUID> findEventIdsByEmail(String email);

    @Query("DELETE FROM EmailBuffer e WHERE e.eventId = :eventId AND e.email = :email")
    void deleteByEmailAndEventId(String email, UUID eventId);

    List<EmailBuffer> findAllByEventId(UUID eventId);

    boolean existsByEmailAndEventId(String email, UUID eventId);

    void deleteByEventId(UUID eventId);

    void deleteByEmail(String email);
}
