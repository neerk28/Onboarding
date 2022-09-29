package com.intuitcraft.onboarding.controller;

import com.intuitcraft.onboarding.dto.AccessToken;
import com.intuitcraft.onboarding.entity.DriverEntity;
import com.intuitcraft.onboarding.dto.SendOtpRequest;
import com.intuitcraft.onboarding.dto.SendOtpResponse;
import com.intuitcraft.onboarding.dto.ValidateOtpRequest;
import com.intuitcraft.onboarding.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/otp")
public class OTPController {

    @Autowired
    OTPService otpService;

    @PostMapping("/send")
    public ResponseEntity<SendOtpResponse> sendLoginOtp(@RequestBody SendOtpRequest sendOtpRequest){
        SendOtpResponse sendOtpResponse = otpService.sendOtp(sendOtpRequest);
        return new ResponseEntity<>(sendOtpResponse, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<AccessToken> verifyOtp(@Valid @RequestBody ValidateOtpRequest validateOtpRequest){
        AccessToken accessToken = otpService.verifyOtp(validateOtpRequest);
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}
