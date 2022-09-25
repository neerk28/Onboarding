package com.intuitcraft.onboarding.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OtpToken {

    String email;
    String phoneNumber;
    Intent intent;
    String otp;
    int incorrectOtpCount;
    boolean otpVerified;
}
