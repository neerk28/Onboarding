package com.intuitcraft.onboarding.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "driverPrefAndConsent")
@Getter
@Setter
public class DriverPrefAndConsent {

    @EmbeddedId
    private ConsentId consentId;
    private String version;
    private String acceptanceDate;
    private boolean accepted;
}
