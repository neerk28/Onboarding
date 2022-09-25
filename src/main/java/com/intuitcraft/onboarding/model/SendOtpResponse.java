package com.intuitcraft.onboarding.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SendOtpResponse {

    String otpToken;
}
