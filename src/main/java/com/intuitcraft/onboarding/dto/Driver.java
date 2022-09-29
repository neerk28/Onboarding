package com.intuitcraft.onboarding.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class Driver extends User{

    @NotBlank(message = "Language Preference is required")
    @Size(min=5, max = 6, message = "Invalid Locale")
    private String langPreference;

    @NotBlank(message = "City is required")
    private String city;

}
