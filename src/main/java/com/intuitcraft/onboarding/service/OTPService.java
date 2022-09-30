package com.intuitcraft.onboarding.service;

import com.intuitcraft.onboarding.cache.OTPCache;
import com.intuitcraft.onboarding.dto.enums.Intent;
import com.intuitcraft.onboarding.exceptionHandler.IdentityException;
import com.intuitcraft.onboarding.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(OTPService.class);
    public SendOtpResponse sendOtp(SendOtpRequest sendOtpRequest) {
        SendOtpResponse response = null;
        Intent intent = sendOtpRequest.getIntent();
        String email = sendOtpRequest.getEmail();
        String phoneNumber = sendOtpRequest.getPhoneNumber();
        int otpCount = cache.getIncorrectOtpCount(intent +
                getOtpKey(email, phoneNumber));

        if( otpCount < maxIncorrectOtpCount){
            //sendOTP - identity microservice which sends otp to user's email or phoneNumber
            if(true){
                OtpToken otpTokenModel = OtpToken.builder()
                        .email(email)
                        .phoneNumber(phoneNumber)
                        .intent(intent).build();
                String otpToken = getOtpKey(email, phoneNumber) + KEY_SEPARATOR + intent + new Timestamp(System.currentTimeMillis());
                cache.cacheData(otpToken, otpTokenModel, Duration.ofMinutes(ttl));
                response = SendOtpResponse.builder().otpToken(cache.getKey(otpToken)).build();
                LOGGER.info("OTP token generated for {}" , getOtpKey(email,phoneNumber));
            }
        }else {
            LOGGER.error("User blocked for {} minutes" , ttl);
            throw new IdentityException("USER_BLOCKED", "User blocked for " + ttl + " minutes", HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    private String getOtpKey(String email, String phoneNumber) {
        return phoneNumber != null ? phoneNumber : email;
    }

    public AccessToken verifyOtp(ValidateOtpRequest validateOtpRequest) {

        OtpToken otpToken = cache.getData(validateOtpRequest.getOtpToken(), OtpToken.class);
        if(otpToken == null){
            LOGGER.error("Incorrect otp token passed");
            throw new IdentityException("INVALID_TOKEN"," Incorrect otp token", HttpStatus.BAD_REQUEST);
        }
        String otpCountKey = otpToken.getIntent() + getOtpKey(otpToken.getEmail(), otpToken.getPhoneNumber());
        int otpCount = cache.updateOtpCount(otpCountKey);
        if( otpCount < maxIncorrectOtpCount){
            otpToken.setIncorrectOtpCount(otpCount);
            //verifyOtp - identity microservice which sends otp to user's email or phoneNumber
            if(validateOtpRequest.getOtp().equals("123456")){
                otpToken.setOtp(validateOtpRequest.getOtp());
                otpToken.setOtpVerified(true);
                cache.clearCache(otpCountKey);
                cache.cacheData(validateOtpRequest.getOtpToken(), otpToken, Duration.ofMinutes(ttl));
                LOGGER.info("OTP token and OTP verified for {}" , getOtpKey(otpToken.getEmail(), otpToken.getPhoneNumber()));
                return new AccessToken("abcd1234efgh789tfdcty==");
            }else {
                LOGGER.error("Incorrect otp passed - remaining attempt {}" ,(maxIncorrectOtpCount - otpToken.getIncorrectOtpCount()));
                throw new IdentityException("INCORRECT_OTP", "Attempt Remaining : " + (maxIncorrectOtpCount - otpToken.getIncorrectOtpCount()), HttpStatus.BAD_REQUEST);
            }
        }else {
            LOGGER.error("Incorrect attempts exhausted for {}" , otpCountKey);
            throw new IdentityException("USER_BLOCKED", "User blocked for " + ttl + " minutes", HttpStatus.UNAUTHORIZED);
        }
    }
}
