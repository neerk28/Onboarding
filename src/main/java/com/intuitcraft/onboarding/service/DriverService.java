package com.intuitcraft.onboarding.service;

import com.intuitcraft.onboarding.entity.*;
import com.intuitcraft.onboarding.model.*;
import com.intuitcraft.onboarding.repository.DriverOnboardStatusRepository;
import com.intuitcraft.onboarding.repository.DriverPrefAndConsentRepository;
import com.intuitcraft.onboarding.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    DriverPrefAndConsentRepository consentRepository;

    @Autowired
    DriverOnboardStatusRepository driverOnboardStatusRepository;

    public Driver createDriver(Driver driver) {
        Driver newDriver = driverRepository.save(driver);
        for(FileType fileType : FileType.values()){
            DriverOnboardStatus driverOnboardStatus = new DriverOnboardStatus();
            driverOnboardStatus.setFileId(new FileId(newDriver.getId(),fileType));
            driverOnboardStatus.setStatus(Status.TO_BE_DONE);
            driverOnboardStatusRepository.save(driverOnboardStatus);
        }
        return newDriver;
    }

    public Driver getDriverById(Long id){
        Optional<Driver> driver = driverRepository.findById(id);
        if(driver.isPresent()) {
            return driver.get();
        }else {
            throw new EntityNotFoundException("Driver " + id + " not found");
        }
    }

    public boolean validateRegistration(PublicCredentials publicCredentials) {
        if(publicCredentials.getPhoneNumber() != null)
            return driverRepository.findByPhoneNumber(publicCredentials.getPhoneNumber()).size() == 0;
        else if(publicCredentials.getEmail() != null)
            return driverRepository.findByEmail(publicCredentials.getEmail()).size() == 0;
        return false;
    }

    public void savePrefAndConsent(PrefAndConsent prefAndConsent) {
        List<DriverPrefAndConsent> list = new ArrayList<>();
        getDriverById(prefAndConsent.getDriverId());

        for(Consent consent: prefAndConsent.getConsents()){
            DriverPrefAndConsent driverPrefAndConsent = new DriverPrefAndConsent();
            driverPrefAndConsent.setAcceptanceDate(prefAndConsent.getAcceptedDate());
            ConsentId consentId = new ConsentId();
            consentId.setDocType(consent.getDocType());
            consentId.setId(prefAndConsent.getDriverId());
            driverPrefAndConsent.setConsentId(consentId);
            driverPrefAndConsent.setVersion(consent.getVersion());
            driverPrefAndConsent.setAccepted(consent.isAccepted());
            consentRepository.save(driverPrefAndConsent);
            list.add(driverPrefAndConsent);
        }
        // consentRepository.saveAll(list);
    }
}
