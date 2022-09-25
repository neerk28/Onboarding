package com.intuitcraft.onboarding.controller;

import com.intuitcraft.onboarding.dto.Driver;
import com.intuitcraft.onboarding.model.SendOtpRequest;
import com.intuitcraft.onboarding.model.SendOtpRequestModel;
import com.intuitcraft.onboarding.model.SendOtpResponse;
import com.intuitcraft.onboarding.model.ValidateOtpRequest;
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
@RequestMapping("v1/identity/otp")
public class OTPController {

    @Autowired
    OTPService otpService;

    @PostMapping("/send")
    public ResponseEntity<SendOtpResponse> sendLoginOtp(@Valid @RequestBody SendOtpRequest sendOtpRequest){
        SendOtpResponse sendOtpResponse = otpService.sendOtp(sendOtpRequest);
        return new ResponseEntity<>(sendOtpResponse, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<Driver> verifyOtp(@Valid @RequestBody ValidateOtpRequest validateOtpRequest){
        boolean isValid = otpService.verifyOtp(validateOtpRequest);
        if(isValid)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }


}
