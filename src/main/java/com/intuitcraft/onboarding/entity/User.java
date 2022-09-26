package com.intuitcraft.onboarding.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@MappedSuperclass
@ToString
public class User {

    @NotBlank
    private String firstName;
    @NotBlank
    private String surName;
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    @Size(min=5, max = 6)
    private String langPreference;
}
