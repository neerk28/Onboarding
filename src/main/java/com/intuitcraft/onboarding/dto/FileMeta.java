package com.intuitcraft.onboarding.dto;

import com.intuitcraft.onboarding.dto.enums.FileType;
import lombok.Data;

@Data
public class FileMeta {

    private FileType fileType;
    private Long id;
}
