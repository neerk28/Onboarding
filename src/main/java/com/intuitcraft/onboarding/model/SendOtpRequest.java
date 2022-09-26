package com.intuitcraft.onboarding.model;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class SendOtpRequest {

    String email;
    String phoneNumber;
    Intent intent;
}
