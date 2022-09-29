package com.intuitcraft.onboarding.repository;

import com.intuitcraft.onboarding.entity.DriverConsentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverConsentsRepository extends JpaRepository<DriverConsentEntity, Long> {
}
