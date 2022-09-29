package com.intuitcraft.onboarding.repository;

import com.intuitcraft.onboarding.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverProfileRepository extends JpaRepository<DriverEntity, Long> {

    List<DriverEntity> findByPhoneNumber(String phoneNumber);

    List<DriverEntity> findByEmail(String email);
}
