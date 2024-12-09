package com.collaberadigital.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BorrowerDto(
        Long id,
        @Email(message = "Invalid email format") @NotBlank(message = "Email cannot be blank") String email,
        @NotBlank(message = "Name cannot be blank") String name
) {
    public BorrowerDto withUpdatedEmail(String email) {
        return new BorrowerDto(this.id, email, this.name);
    }
}
