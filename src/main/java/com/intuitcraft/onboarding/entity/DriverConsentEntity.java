package com.intuitcraft.onboarding.entity;

import com.intuitcraft.onboarding.dto.enums.DocType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "driver_consents")
@Getter
@Setter
public class DriverConsentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consentId;
    private Long id;
    private DocType docType;
    private String version;
    private Date accepted_at;
}
