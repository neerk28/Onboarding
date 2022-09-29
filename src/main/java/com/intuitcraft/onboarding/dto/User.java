package com.intuitcraft.onboarding.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class User {

    @NotBlank(message = "FirstName is required")
    private String firstName;

    @NotBlank(message = "SurName is required")
    private String surName;

    @NotBlank(message = "Email is required")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    @NotBlank(message = "PhoneNumber is required")
    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "Invalid phone number")
    private String phoneNumber;
}
