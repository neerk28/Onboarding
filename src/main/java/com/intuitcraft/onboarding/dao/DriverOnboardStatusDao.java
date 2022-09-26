package com.intuitcraft.onboarding.dao;

import com.intuitcraft.onboarding.entity.DriverOnboardStatus;
import com.intuitcraft.onboarding.entity.FileId;
import com.intuitcraft.onboarding.model.FileMeta;
import com.intuitcraft.onboarding.model.Status;
import com.intuitcraft.onboarding.model.StatusInfo;
import com.intuitcraft.onboarding.repository.DriverOnboardStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class DriverOnboardStatusDao {

    @Autowired
    DriverOnboardStatusRepository driverOnboardStatusRepository;

    public void updateDriverOnboardStatus(FileMeta fileMeta, String url, String ticketId) {
        DriverOnboardStatus driverOnboardStatus = new DriverOnboardStatus();
        driverOnboardStatus.setStatus(Status.IN_REVIEW);
        driverOnboardStatus.setUploadedAt(Timestamp.from(Instant.now()));
        driverOnboardStatus.setFileId(new FileId(fileMeta.getId(),fileMeta.getFileType()));
        driverOnboardStatus.setUrl(url);
        driverOnboardStatus.setTicketId(ticketId);
        driverOnboardStatusRepository.save(driverOnboardStatus);
    }

    public List<StatusInfo> getOnboardStatus(Long id) {
        List<DriverOnboardStatus> driverOnboardStatusList = driverOnboardStatusRepository.findAllByFileIdId(id);
        List<StatusInfo> statusInfoList = new ArrayList<>();
        for(DriverOnboardStatus driverOnboardStatus : driverOnboardStatusList){
            StatusInfo statusInfo = new StatusInfo(driverOnboardStatus.getFileId().getFileType(), driverOnboardStatus.getStatus());
            statusInfoList.add(statusInfo);
        }
        return statusInfoList;
    }
}
