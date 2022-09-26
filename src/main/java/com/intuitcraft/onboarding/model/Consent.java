package com.intuitcraft.onboarding.model;

import lombok.Value;

@Value
public class Consent {

    private String docType;
    private boolean accepted;
    private String version;
}
