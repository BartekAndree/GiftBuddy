package com.giftbuddygroup.giftbuddy.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giftbuddygroup.giftbuddy.model.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    boolean existsByIdAndOrganizerId(UUID eventId, UUID organizerId);

    List<Event> findAllByEndDateAndIsActive(LocalDate endDate, boolean isActive);
}
