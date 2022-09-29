package com.intuitcraft.onboarding.controller;

import com.intuitcraft.onboarding.entity.DriverEntity;
import com.intuitcraft.onboarding.dto.PublicCredentials;
import com.intuitcraft.onboarding.service.DriverProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validate")
public class ValidationController {

    @Autowired
    DriverProfileService driverService;

    @GetMapping("/publicCredentials")
    public ResponseEntity<Void> validatePublicCredentials(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "phoneNumber", required = false) String phoneNumber){
        PublicCredentials publicCredentials = PublicCredentials.PublicCredentialsBuilder.newInstance()
                .setPhoneNumber(phoneNumber)
                .setEmail(email)
                .build();

        boolean isValid = driverService.validateRegistration(publicCredentials);
        if(isValid)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
