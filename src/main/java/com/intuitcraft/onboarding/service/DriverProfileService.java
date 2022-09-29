package com.intuitcraft.onboarding.service;

import com.intuitcraft.onboarding.dto.*;
import com.intuitcraft.onboarding.dto.enums.FileType;
import com.intuitcraft.onboarding.dto.enums.FileUploadStatus;
import com.intuitcraft.onboarding.entity.DriverCommunicationPreferenceEntity;
import com.intuitcraft.onboarding.entity.DriverEntity;
import com.intuitcraft.onboarding.entity.DriverOnboardStatusEntity;
import com.intuitcraft.onboarding.entity.DriverConsentEntity;
import com.intuitcraft.onboarding.repository.DriverCommunicationPreferencesRepository;
import com.intuitcraft.onboarding.repository.DriverConsentsRepository;
import com.intuitcraft.onboarding.repository.DriverOnboardStatusRepository;
import com.intuitcraft.onboarding.repository.DriverProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DriverProfileService {

    @Autowired
    DriverProfileRepository driverProfileRepository;

    @Autowired
    DriverConsentsRepository consentRepository;

    @Autowired
    DriverCommunicationPreferencesRepository driverCommunicationPreferencesRepository;

    @Autowired
    DriverOnboardStatusRepository driverOnboardStatusRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverProfileService.class);

    public Long createDriverProfileAndOnboardStatus(Driver driver) {
        DriverEntity driverEntity = new DriverEntity();
        BeanUtils.copyProperties(driver, driverEntity);
        DriverEntity newDriver = null;
        try {
            newDriver = driverProfileRepository.save(driverEntity);
            LOGGER.info("Driver Profile {} created successfully", newDriver.getId());
            prepareDriverOnboardStatus(newDriver.getId());
            LOGGER.info("Driver {} Onboard Status created successfully", newDriver.getId());
        }catch (Exception e){
            LOGGER.error("Driver Profile creation failed", e.getMessage());
            if(e.getMessage().contains("driver.email")){
                throw new DataIntegrityViolationException("Entered email already exists");
            }else if(e.getMessage().contains("driver.phone")){
                throw new DataIntegrityViolationException("Entered phoneNumber already exists");
            }
            throw e;
        }
        return newDriver.getId();
    }

    private void prepareDriverOnboardStatus(Long id) {
        for (FileType fileType : FileType.values()) {
            DriverOnboardStatusEntity driverOnboardStatusEntity = new DriverOnboardStatusEntity();
            driverOnboardStatusEntity.setDriverId(id);
            driverOnboardStatusEntity.setFileType(fileType);
            driverOnboardStatusEntity.setFileUploadStatus(FileUploadStatus.TO_BE_DONE);
            driverOnboardStatusRepository.save(driverOnboardStatusEntity);
        }
    }

    public Driver getDriverById(Long id){
        Optional<DriverEntity> optionalDriverEntity = driverProfileRepository.findById(id);
        if(optionalDriverEntity.isPresent()) {
            DriverEntity driverEntity = optionalDriverEntity.get();
            Driver driver = new Driver();
            BeanUtils.copyProperties(driverEntity, driver);
            return driver;
        }else {
            LOGGER.error("Driver Profile {} not found", id);
            throw new EntityNotFoundException("Driver Profile " + id + " not found");
        }
    }

    public boolean validateRegistration(PublicCredentials publicCredentials) {
        if(publicCredentials.getPhoneNumber() != null)
            return driverProfileRepository.findByPhoneNumber(publicCredentials.getPhoneNumber()).size() == 0;
        else if(publicCredentials.getEmail() != null)
            return driverProfileRepository.findByEmail(publicCredentials.getEmail()).size() == 0;
        return false;
    }

    public void saveConsents(DriverConsent driverConsent) {
        getDriverById(driverConsent.getDriverId());

        for(Consent consent: driverConsent.getConsents()){
            DriverConsentEntity driverConsentEntity = new DriverConsentEntity();
            driverConsentEntity.setAccepted_at(driverConsent.getAcceptedAt());
            driverConsentEntity.setId(driverConsent.getDriverId());
            driverConsentEntity.setDocType(consent.getDocType());
            driverConsentEntity.setVersion(consent.getVersion());
            consentRepository.save(driverConsentEntity);
        }
        LOGGER.info("Driver Consents {} saved successfully", driverConsent.getDriverId());
    }

    public void saveCommunicationPreferences(DriverPreference driverPreferences) {
        getDriverById(driverPreferences.getDriverId());
        for(Preference driverPreference : driverPreferences.getPreferences()){
            DriverCommunicationPreferenceEntity entity = new DriverCommunicationPreferenceEntity();
            entity.setPreferenceType(driverPreference.getPreferenceType());
            entity.setAllowed(driverPreference.isAllowed());
            entity.setDriverId(driverPreferences.getDriverId());
            driverCommunicationPreferencesRepository.save(entity);
        }
        LOGGER.info("Driver Communication Preferences {} saved successfully", driverPreferences.getDriverId());

    }

    public DriverPreference getCommunicationPreferencesForId(Long id) {
        getDriverById(id);
        List<DriverCommunicationPreferenceEntity> entities = driverCommunicationPreferencesRepository.findByDriverId(id);
        List<Preference> driverPreferenceList = new ArrayList<>();
        DriverPreference driverPreference = new DriverPreference();
        for(DriverCommunicationPreferenceEntity entity : entities){
            Preference preference = new Preference();
            preference.setPreferenceType(entity.getPreferenceType());
            preference.setAllowed(entity.isAllowed());
            driverPreferenceList.add(preference);
        }
        driverPreference.setPreferences(driverPreferenceList);
        return driverPreference;
    }

    public List<StatusInfo> getOnboardApplicationStatus(Long id) {
        getDriverById(id);
        List<DriverOnboardStatusEntity> driverOnboardStatusEntities = driverOnboardStatusRepository.findByDriverId(id);
        List<StatusInfo> statusInfoList = new ArrayList<>();
        for(DriverOnboardStatusEntity entity : driverOnboardStatusEntities){
            StatusInfo statusInfo = new StatusInfo(entity.getFileType(),entity.getFileUploadStatus());
            statusInfoList.add(statusInfo);
        }
        return statusInfoList;
    }

    public void updateDriverOnboardStatus(FileMeta fileMeta, String url, String ticketId) {
        DriverOnboardStatusEntity driverOnboardStatusEntity = driverOnboardStatusRepository.findByDriverIdAndFileType(fileMeta.getId(), fileMeta.getFileType());
        driverOnboardStatusEntity.setFileUploadStatus(FileUploadStatus.IN_REVIEW);
        driverOnboardStatusEntity.setUploadedAt(Date.from(Instant.now()));
        driverOnboardStatusEntity.setStorageUrl(url);
        driverOnboardStatusEntity.setTicketId(ticketId);
        driverOnboardStatusRepository.save(driverOnboardStatusEntity);
    }
}
