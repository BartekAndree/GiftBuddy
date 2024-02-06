package com.giftbuddygroup.giftbuddy.model.dto.request;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequestDTO {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    private String username;

    @Size(min = 4, max = 30, message = "First name must be between 4 and 30 characters")
    private String firstName;

    @Size(min = 4, max = 30, message = "Last name must be between 4 and 30 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 9, message = "Phone number can't be longer than 9 characters")
    private String phoneNumber;

    @NotNull(message = "External ID cannot be null")
    private UUID externalId;
}
