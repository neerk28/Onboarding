package com.intuitcraft.onboarding.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "driver")
@Getter
@Setter
@ToString
public class Driver extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(mappedBy = "driver")
//    private Set<DriverPrefAndConsent> prefsAndConsents = new HashSet<>();
}
