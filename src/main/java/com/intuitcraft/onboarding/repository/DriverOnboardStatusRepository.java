package com.intuitcraft.onboarding.repository;

import com.intuitcraft.onboarding.entity.DriverOnboardStatus;
import com.intuitcraft.onboarding.entity.FileId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverOnboardStatusRepository extends JpaRepository<DriverOnboardStatus, FileId> {

    DriverOnboardStatus findByFileId(FileId fileId);

    List<DriverOnboardStatus> findAllByFileIdId(Long id);
}
