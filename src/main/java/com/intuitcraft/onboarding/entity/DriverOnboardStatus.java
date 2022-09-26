package com.intuitcraft.onboarding.entity;

import com.intuitcraft.onboarding.model.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "driver_onboard_status")
@Getter
@Setter
public class DriverOnboardStatus {

    @EmbeddedId
    private FileId fileId;
    private String url;
    private Status status;
    private Timestamp uploadedAt;
    private String ticketId;
}
