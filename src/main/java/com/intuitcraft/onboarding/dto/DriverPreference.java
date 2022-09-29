package com.intuitcraft.onboarding.dto;

import com.intuitcraft.onboarding.dto.enums.PreferenceType;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DriverPreference {

    @Valid
    private List<Preference> preferences;
    @NotNull(message = "Driver Id is required")
    private Long driverId;
}
