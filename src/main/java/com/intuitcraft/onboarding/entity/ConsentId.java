package com.intuitcraft.onboarding.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ConsentId implements Serializable {

    private Long id;
    private String docType;
}
