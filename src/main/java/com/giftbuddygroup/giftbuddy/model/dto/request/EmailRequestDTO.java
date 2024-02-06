package com.giftbuddygroup.giftbuddy.model.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDTO {
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be blank")
    private String email;
}
