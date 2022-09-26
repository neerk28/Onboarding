package com.intuitcraft.onboarding.service;

import com.intuitcraft.onboarding.cache.OTPCache;
import com.intuitcraft.onboarding.exceptionHandler.IdentityException;
import com.intuitcraft.onboarding.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;

@Service
public class OTPService {

    @Value("${max.incorrect.otp}")
    private int maxIncorrectOtpCount;

    @Value("${redis.otpToken.ttl}")
    private int ttl;

    private final String KEY_SEPARATOR = "#";

    @Autowired
    OTPCache cache;
    public SendOtpResponse sendOtp(SendOtpRequest sendOtpRequest) {
        SendOtpResponse response = null;
        Intent intent = sendOtpRequest.getIntent();
        String email = sendOtpRequest.getEmail();
        String phoneNumber = sendOtpRequest.getPhoneNumber();
        int otpCount = cache.getIncorrectOtpCount(intent +
                getOtpKey(email, phoneNumber));

        if( otpCount < maxIncorrectOtpCount){
            //sendOTP - another microservice which sends otp to user's email or phoneNumber
            if(true){
                OtpToken otpTokenModel = OtpToken.builder()
                        .email(email)
                        .phoneNumber(phoneNumber)
                        .intent(intent).build();
                String otpToken = getOtpKey(email, phoneNumber) + KEY_SEPARATOR + intent + new Timestamp(System.currentTimeMillis());
                cache.cacheData(otpToken, otpTokenModel, Duration.ofMinutes(ttl));
                response = SendOtpResponse.builder().otpToken(cache.getKey(otpToken)).build();
            }
        }else {
            throw new IdentityException("USER_BLOCKED", "User blocked for " + ttl + " minutes", HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    private String getOtpKey(String email, String phoneNumber) {
        return phoneNumber != null ? phoneNumber : email;
    }

    public boolean verifyOtp(ValidateOtpRequest validateOtpRequest) {

        OtpToken otpToken = cache.getData(validateOtpRequest.getOtpToken(), OtpToken.class);
        if(otpToken == null){
            throw new IdentityException("INVALID_TOKEN"," Incorrect otp token", HttpStatus.BAD_REQUEST);
        }
        String otpCountKey = otpToken.getIntent() + getOtpKey(otpToken.getEmail(), otpToken.getPhoneNumber());
        int otpCount = cache.updateOtpCount(otpCountKey);
        if( otpCount < maxIncorrectOtpCount){
            otpToken.setIncorrectOtpCount(otpCount);
            //verifyOtp - another microservice which sends otp to user's email or phoneNumber
            if(validateOtpRequest.getOtp().equals("123456")){
                otpToken.setOtp(validateOtpRequest.getOtp());
                otpToken.setOtpVerified(true);
                cache.clearCache(otpCountKey);
                cache.cacheData(validateOtpRequest.getOtpToken(), otpToken, Duration.ofMinutes(ttl));
                return true;
            }else {
                throw new IdentityException("INCORRECT_OTP", "Attempt Remaining : " + (maxIncorrectOtpCount - otpToken.getIncorrectOtpCount()), HttpStatus.BAD_REQUEST);
            }
        }else {
            throw new IdentityException("USER_BLOCKED", "User blocked for " + ttl + " minutes", HttpStatus.UNAUTHORIZED);
        }
    }
}
