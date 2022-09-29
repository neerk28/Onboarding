package com.intuitcraft.onboarding.entity;

import com.intuitcraft.onboarding.dto.enums.PreferenceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "driver_communication_preferences")
@Getter
@Setter
public class DriverCommunicationPreferenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prefId;
    private PreferenceType preferenceType;
    private boolean allowed;
    @Column(name = "id")
    private Long driverId;
}
