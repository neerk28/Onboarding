package com.intuitcraft.onboarding.dto;

import com.intuitcraft.onboarding.dto.enums.Intent;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class SendOtpRequest {

    String email;
    String phoneNumber;
    Intent intent;
}
