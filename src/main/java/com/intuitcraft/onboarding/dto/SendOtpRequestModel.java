package com.intuitcraft.onboarding.dto;


import com.intuitcraft.onboarding.dto.enums.Intent;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Value
public class SendOtpRequestModel {
    String email;
    String phoneNumber;
    Intent intent;
}
