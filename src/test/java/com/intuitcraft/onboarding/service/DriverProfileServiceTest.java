package com.intuitcraft.onboarding.service;

import com.intuitcraft.onboarding.dto.Driver;
import com.intuitcraft.onboarding.entity.DriverEntity;
import com.intuitcraft.onboarding.entity.DriverOnboardStatusEntity;
import com.intuitcraft.onboarding.repository.DriverCommunicationPreferencesRepository;
import com.intuitcraft.onboarding.repository.DriverConsentsRepository;
import com.intuitcraft.onboarding.repository.DriverOnboardStatusRepository;
import com.intuitcraft.onboarding.repository.DriverProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class DriverProfileServiceTest {

    @InjectMocks
    DriverProfileService driverProfileService;

    @Mock
    DriverProfileRepository driverProfileRepository;

    @Mock
    DriverConsentsRepository consentRepository;

    @Mock
    DriverCommunicationPreferencesRepository driverCommunicationPreferencesRepository;

    @Mock
    DriverOnboardStatusRepository driverOnboardStatusRepository;

    @Test
    public void createDriverProfileAndOnboardStatus(){
        DriverEntity entity = new DriverEntity();
        entity.setId(123L);
        Mockito.when(driverProfileRepository.save(any(DriverEntity.class))).thenReturn(entity);
        Mockito.when(driverOnboardStatusRepository.save(any(DriverOnboardStatusEntity.class))).thenReturn(new DriverOnboardStatusEntity());
        Long id = driverProfileService.createDriverProfileAndOnboardStatus(new Driver());
        assertEquals(123L, id);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createDriverProfileAndOnboardStatusException(){
        Mockito.when(driverProfileRepository.save(any(DriverEntity.class))).thenThrow(new DataIntegrityViolationException("driver.email already exists"));
        driverProfileService.createDriverProfileAndOnboardStatus(new Driver());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createDriverProfileAndOnboardStatusPhoneNumberException(){
        Mockito.when(driverProfileRepository.save(any(DriverEntity.class))).thenThrow(new DataIntegrityViolationException("driver.phoneNumber already exists"));
        driverProfileService.createDriverProfileAndOnboardStatus(new Driver());
    }

    @Test
    public void getDriverById(){
        Mockito.when(driverProfileRepository.findById(any(Long.class))).thenReturn(Optional.of(new DriverEntity()));
        Driver driver = driverProfileService.getDriverById(123L);
        assertNotNull(driver);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getDriverByIdException(){
        Mockito.when(driverProfileRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        driverProfileService.getDriverById(123L);
    }
}
