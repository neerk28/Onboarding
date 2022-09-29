package com.intuitcraft.onboarding.dto;

import com.intuitcraft.onboarding.dto.enums.FileType;
import com.intuitcraft.onboarding.dto.enums.FileUploadStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatusInfo {
    private FileType fileType;
    private FileUploadStatus fileUploadStatus;
}
