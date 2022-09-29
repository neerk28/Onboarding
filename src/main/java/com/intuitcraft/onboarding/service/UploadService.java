package com.intuitcraft.onboarding.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuitcraft.onboarding.dto.FileMeta;
import com.intuitcraft.onboarding.projectManagement.TrelloClient;
import com.intuitcraft.onboarding.storage.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class UploadService {

    @Autowired
    DriverProfileService driverProfileService;
    private ObjectMapper mapper = new ObjectMapper();
    public void upload(String fileName, InputStream inputStream, String meta) throws IOException {

        try {
            FileMeta fileMeta = mapper.readValue(meta, FileMeta.class);
            String url = S3Util.uploadFile(fileName, inputStream);
            if(!url.isEmpty()){
                String ticketId = TrelloClient.createCard(url, fileMeta);
                driverProfileService.updateDriverOnboardStatus(fileMeta, url, ticketId);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

}
