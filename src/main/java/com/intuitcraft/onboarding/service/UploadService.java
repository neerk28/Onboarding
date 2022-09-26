package com.intuitcraft.onboarding.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuitcraft.onboarding.dao.DriverOnboardStatusDao;
import com.intuitcraft.onboarding.entity.DriverOnboardStatus;
import com.intuitcraft.onboarding.entity.FileId;
import com.intuitcraft.onboarding.model.FileMeta;
import com.intuitcraft.onboarding.model.Status;
import com.intuitcraft.onboarding.projectManagement.TrelloClient;
import com.intuitcraft.onboarding.repository.DriverOnboardStatusRepository;
import com.intuitcraft.onboarding.storage.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class UploadService {

    @Autowired
    DriverOnboardStatusDao driverOnboardStatusDao;
    private ObjectMapper mapper = new ObjectMapper();
    public void upload(String fileName, String meta) throws JsonProcessingException {

        try {
            FileMeta fileMeta = mapper.readValue(meta, FileMeta.class);
            String url = S3Util.uploadFile(fileName);
            if(!url.isEmpty()){
                String ticketId = TrelloClient.createCard(url, fileMeta);
                driverOnboardStatusDao.updateDriverOnboardStatus(fileMeta, url, ticketId);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

}
