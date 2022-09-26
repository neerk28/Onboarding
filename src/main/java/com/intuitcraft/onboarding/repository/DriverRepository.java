package com.intuitcraft.onboarding.repository;

import com.intuitcraft.onboarding.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findByPhoneNumber(String phoneNumber);

    List<Driver> findByEmail(String email);
}
