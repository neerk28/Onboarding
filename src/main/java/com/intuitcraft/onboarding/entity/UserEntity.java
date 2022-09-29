package com.intuitcraft.onboarding.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class UserEntity {

    private String firstName;

    private String surName;

    private String email;

    private String phoneNumber;
}
