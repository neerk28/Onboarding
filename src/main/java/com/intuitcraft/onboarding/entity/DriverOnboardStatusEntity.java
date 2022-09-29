package com.intuitcraft.onboarding.entity;

import com.intuitcraft.onboarding.dto.enums.FileType;
import com.intuitcraft.onboarding.dto.enums.FileUploadStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "driver_onboard_status")
@Getter
@Setter
public class DriverOnboardStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    @Column(name = "id")
    private Long driverId;
    private String storageUrl;
    private FileType fileType;
    private FileUploadStatus fileUploadStatus;
    private Date uploadedAt;
    private String fileMetadata;
    private String ticketId;
}
