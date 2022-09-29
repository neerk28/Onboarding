package com.intuitcraft.onboarding.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SendOtpResponse {

    String otpToken;
}
