package com.intuitcraft.onboarding.model;

import lombok.Value;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Value
public class PrefAndConsent {

    private String acceptedDate;
    private List<Consent> consents;
    private Long driverId;
}
