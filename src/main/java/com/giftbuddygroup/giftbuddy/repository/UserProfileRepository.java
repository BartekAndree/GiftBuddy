package com.giftbuddygroup.giftbuddy.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findByUsername(String username);

    Optional<UserProfile> findByEmail(String email);

    @Query("SELECT u.id FROM UserProfile u WHERE u.email = :email")
    Optional<UUID> findIdByEmail(String email);

    boolean existsByExternalId(UUID externalId);

    Optional<UserProfile> findByExternalId(UUID externalId);
}
