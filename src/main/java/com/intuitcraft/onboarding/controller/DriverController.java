package com.intuitcraft.onboarding.controller;

import com.intuitcraft.onboarding.dto.Driver;
import com.intuitcraft.onboarding.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/onboarding")
public class DriverController {

    @Autowired
    DriverService driverService;

    @PostMapping("/drivers")
    public ResponseEntity<Driver> createDriver(@Valid @RequestBody Driver driver){
        Driver newDriver = driverService.createDriver(driver);
        return new ResponseEntity<>(newDriver, HttpStatus.CREATED);
    }

    @GetMapping("/drivers/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id){
        Driver newDriver = driverService.getDriverById(id);
        return new ResponseEntity<>(newDriver, HttpStatus.OK);
    }
}
