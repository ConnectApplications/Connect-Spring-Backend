package com.connectbundle.connect.dto.ProjectApplicationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessProjectApplicationDTO {
    Long applicationId;
    boolean accept;
    String description;
}
