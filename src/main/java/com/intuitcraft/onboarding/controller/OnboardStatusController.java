package com.intuitcraft.onboarding.controller;

import com.intuitcraft.onboarding.dao.DriverOnboardStatusDao;
import com.intuitcraft.onboarding.model.FileUploadResponse;
import com.intuitcraft.onboarding.model.StatusInfo;
import com.intuitcraft.onboarding.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("v1/onboarding/")
public class OnboardStatusController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnboardStatusController.class);
    @Autowired
    UploadService uploadService;

    @Autowired
    DriverOnboardStatusDao driverOnboardStatusDao;

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadDocuments (@RequestParam("file") MultipartFile multipartFile,
    @RequestParam("meta") String meta) {
        //contentType validation
         if (multipartFile != null && multipartFile.getContentType() != null &&
                 !(multipartFile.getContentType().toLowerCase().endsWith("pdf") ||
                         multipartFile.getContentType().toLowerCase().endsWith("jpg") || multipartFile.getContentType().toLowerCase().endsWith("png")))
                throw new MultipartException("Only pdf, jpg, png file formats are supported");
        long size = multipartFile.getSize();
        String fileName = multipartFile.getOriginalFilename();

        FileUploadResponse response = null;
        try {
            uploadService.upload(fileName, meta);
            LOGGER.info("File {} has been uploaded successfully!" , fileName);
            response = new FileUploadResponse();
            response.setFileName(fileName);
            response.setSize(size);
            response.setMessage("Upload success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Error uploading file: " + ex.getMessage());
            response.setMessage("Upload failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<List<StatusInfo>> getOnboardStatus(@PathVariable Long id){

        List<StatusInfo> statusInfo = driverOnboardStatusDao.getOnboardStatus(id);
        return new ResponseEntity<>(statusInfo, HttpStatus.OK);
    }

}
