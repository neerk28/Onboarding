package com.intuitcraft.onboarding.entity;

import com.intuitcraft.onboarding.model.FileType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class FileId implements Serializable {
    private Long id;
    private FileType fileType;

    public FileId(Long id, FileType fileType) {
        this.id = id;
        this.fileType = fileType;
    }
}
