package com.intuitcraft.onboarding.model;


import lombok.Value;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Value
public class SendOtpRequestModel {
    String email;
    String phoneNumber;
    Intent intent;
}
