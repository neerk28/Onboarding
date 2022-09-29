package com.intuitcraft.onboarding.dto;

import com.intuitcraft.onboarding.dto.enums.DocType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Consent {

    @NotNull(message = "Doc type is required")
    private DocType docType;
    @NotBlank(message = "Version is required")
    private String version;
}
