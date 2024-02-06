package com.giftbuddygroup.giftbuddy.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "email_buffer")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailBuffer extends BaseEntity {

    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "email", nullable = false, length = 100)
    private String email;
}
