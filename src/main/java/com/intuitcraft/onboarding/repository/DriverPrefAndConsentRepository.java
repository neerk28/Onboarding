package com.intuitcraft.onboarding.repository;

import com.intuitcraft.onboarding.entity.ConsentId;
import com.intuitcraft.onboarding.entity.DriverPrefAndConsent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverPrefAndConsentRepository extends JpaRepository<DriverPrefAndConsent, ConsentId> {
}
