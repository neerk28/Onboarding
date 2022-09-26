package com.intuitcraft.onboarding.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponse {
    private String fileName;
    private long size;
    private String message;
}
