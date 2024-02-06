package com.giftbuddygroup.giftbuddy.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import lombok.*;

@Entity
@Table(name = "user_profile")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserProfile extends BaseEntity {

    @Column(name = "external_id", unique = true)
    @NotNull(message = "External ID cannot be null")
    private UUID externalId;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    private String username;

    @Column(name = "first_name", length = 50)
    @Size(min = 4, max = 30, message = "First name must be between 4 and 30 characters")
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(min = 4, max = 30, message = "Last name must be between 4 and 30 characters")
    private String lastName;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    @Email(message = "Email is invalid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @Column(name = "phone_number", length = 9)
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must be numeric")
    private String phoneNumber;

    public UserProfile copy() {
        UserProfile copiedProfile = new UserProfile();

        copiedProfile.setId(this.getId());
        copiedProfile.setUsername(this.getUsername());
        copiedProfile.setFirstName(this.getFirstName());
        copiedProfile.setLastName(this.getLastName());
        copiedProfile.setEmail(this.getEmail());
        copiedProfile.setPhoneNumber(this.getPhoneNumber());

        return copiedProfile;
    }
}
