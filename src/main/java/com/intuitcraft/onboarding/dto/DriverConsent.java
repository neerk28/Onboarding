package com.intuitcraft.onboarding.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class DriverConsent {

    @NotNull(message = "Accepted Date is required")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date acceptedAt;
    @Valid
    private List<Consent> consents;
    @NotNull(message = "Driver Id is required")
    private Long driverId;
}
