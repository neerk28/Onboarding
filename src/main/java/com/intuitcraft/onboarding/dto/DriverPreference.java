package com.intuitcraft.onboarding.dto;

import com.intuitcraft.onboarding.dto.enums.PreferenceType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DriverPreference {

    @NotNull(message = "Mode is required")
    private PreferenceType preferenceType;
    private boolean allowed;
}
