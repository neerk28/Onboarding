package com.intuitcraft.onboarding.service;

import com.intuitcraft.onboarding.model.Driver;
import com.intuitcraft.onboarding.model.PublicCredentials;
import com.intuitcraft.onboarding.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    DriverRepository repository;

    public Driver createDriver(Driver driver) {
        Driver newDriver = repository.save(driver);
        System.out.println(newDriver);
        return newDriver;
    }

    public Driver getDriverById(Long id){
        Optional<Driver> driver = repository.findById(id);
        if(driver.isPresent()) {
            return driver.get();
        }else {
            throw new EntityNotFoundException("Driver " + id + " not found");
        }
    }

    public boolean validateRegistration(PublicCredentials publicCredentials) {
        if(publicCredentials.getPhoneNumber() != null)
            return repository.findByPhoneNumber(publicCredentials.getPhoneNumber()).size() == 0;
        else if(publicCredentials.getEmail() != null)
            return repository.findByEmail(publicCredentials.getEmail()).size() == 0;
        return false;
    }
}
