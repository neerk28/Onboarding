package com.intuitcraft.onboarding.controller;


import com.intuitcraft.onboarding.dto.Driver;
import com.intuitcraft.onboarding.dto.DriverConsent;
import com.intuitcraft.onboarding.dto.DriverPreference;
import com.intuitcraft.onboarding.service.DriverProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profile")
public class DriverProfileController {

    @Autowired
    DriverProfileService driverProfileService;

    @PostMapping
    public ResponseEntity<Object> createProfile(@Valid @RequestBody Driver driver){
        Long driverProfileId = driverProfileService.createDriverProfileAndOnboardStatus(driver);
        Map<String, Long> profile = new HashMap<>();
        profile.put("id",driverProfileId);
        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getProfileById(@PathVariable @NotBlank(message = "Id is required") Long id){
        Driver driver = driverProfileService.getDriverById(id);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PostMapping("/consents")
    public ResponseEntity<Void> saveConsents(@Valid @RequestBody DriverConsent driverConsent){
        driverProfileService.saveConsents(driverConsent);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/preferences")
    public ResponseEntity<Void> saveCommunicationPreference(@PathVariable @NotBlank(message = "Id is required") Long id, @Valid @RequestBody List<DriverPreference> driverPreferences){
        driverProfileService.saveCommunicationPreferences(id, driverPreferences);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/preferences")
    public ResponseEntity<List<DriverPreference>> getCommunicationPreference(@PathVariable @NotBlank(message = "Id is required") Long id){
        List<DriverPreference> driverPreferences = driverProfileService.getCommunicationPreferencesForId(id);
        return new ResponseEntity<>(driverPreferences, HttpStatus.OK);
    }
}
