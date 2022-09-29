package com.intuitcraft.onboarding.controller;

import com.intuitcraft.onboarding.dto.FileUploadResponse;
import com.intuitcraft.onboarding.dto.StatusInfo;
import com.intuitcraft.onboarding.service.DriverProfileService;
import com.intuitcraft.onboarding.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationStatusController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStatusController.class);
    @Autowired
    UploadService uploadService;

    @Autowired
    DriverProfileService driverProfileService;

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadDocuments(@RequestParam("file") MultipartFile multipartFile,
                                                  @RequestParam("meta") @NotBlank(message = "Meta is required") String meta) {
        //contentType validation
        if (multipartFile != null && multipartFile.getContentType() != null &&
                !(multipartFile.getContentType().toLowerCase().endsWith("pdf") ||
                        multipartFile.getContentType().toLowerCase().endsWith("jpg") || multipartFile.getContentType().toLowerCase().endsWith("png")))
            throw new MultipartException("Only pdf, jpg, png file formats are supported");

        String fileName = multipartFile.getOriginalFilename();

        FileUploadResponse response = null;
        try {
            uploadService.upload(fileName, multipartFile.getInputStream(), meta);
            LOGGER.info("File {} has been uploaded successfully!" , fileName);
            response = new FileUploadResponse();
            response.setFileName(fileName);
            response.setMessage("Upload success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Error uploading file: " + ex.getMessage());
            response.setMessage("Upload failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<List<StatusInfo>> getOnboardApplicationStatus(@PathVariable @NotBlank(message = "Id is required") Long id){
        List<StatusInfo> statusInfo = driverProfileService.getOnboardApplicationStatus(id);
        return new ResponseEntity<>(statusInfo, HttpStatus.OK);
    }

}
