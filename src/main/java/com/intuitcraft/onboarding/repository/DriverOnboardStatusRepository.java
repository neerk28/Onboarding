package com.intuitcraft.onboarding.repository;

import com.intuitcraft.onboarding.dto.enums.FileType;
import com.intuitcraft.onboarding.entity.DriverOnboardStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverOnboardStatusRepository extends JpaRepository<DriverOnboardStatusEntity, Long> {

    List<DriverOnboardStatusEntity> findByDriverId(Long id);
    DriverOnboardStatusEntity findByDriverIdAndFileType(Long id, FileType fileType);
}
