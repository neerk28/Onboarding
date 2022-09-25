package com.intuitcraft.onboarding.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Builder(toBuilder = true)
@Value
public class SendOtpRequest {

    String email;
    String phoneNumber;
    @NotBlank(message="intent cannot be null")
    Intent intent;
}
