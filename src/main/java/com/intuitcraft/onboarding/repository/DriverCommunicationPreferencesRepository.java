package com.intuitcraft.onboarding.repository;

import com.intuitcraft.onboarding.entity.DriverCommunicationPreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverCommunicationPreferencesRepository extends JpaRepository<DriverCommunicationPreferenceEntity, Long> {

    List<DriverCommunicationPreferenceEntity> findByDriverId(Long id);
}
