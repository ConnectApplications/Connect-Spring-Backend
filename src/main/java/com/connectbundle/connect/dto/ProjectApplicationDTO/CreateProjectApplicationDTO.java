package com.connectbundle.connect.dto.ProjectApplicationDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectApplicationDTO {
    private Long projectId;
    private String applicantUserName;
    private boolean mentorRequest;
}
