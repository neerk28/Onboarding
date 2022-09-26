package com.intuitcraft.onboarding.controller;

import com.intuitcraft.onboarding.entity.Driver;
import com.intuitcraft.onboarding.model.PublicCredentials;
import com.intuitcraft.onboarding.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/validate")
public class ValidationController {

    @Autowired
    DriverService driverService;

    @GetMapping("/publicCredentials")
    public ResponseEntity<Driver> validatePublicCredentials(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "phoneNumber", required = false) String phoneNumber){
        PublicCredentials publicCredentials = PublicCredentials.PublicCredentialsBuilder.newInstance()
                .setPhoneNumber(phoneNumber)
                .setEmail(email)
                .build();

        boolean isValid = driverService.validateRegistration(publicCredentials);
        if(isValid)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.FOUND);
    }

}
