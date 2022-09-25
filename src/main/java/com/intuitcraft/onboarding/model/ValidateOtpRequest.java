package com.intuitcraft.onboarding.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class ValidateOtpRequest {

    @NotBlank(message = "otpToken cannot be null or empty")
    private String otpToken;
    @NotBlank(message = "otp cannot be null or empty")
    private String otp;
}
