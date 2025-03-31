package com.connectbundle.connect.dto.ProjectApplicationDTO;

import com.connectbundle.connect.dto.UserDTO.BaseUserResponseDTO;
import com.connectbundle.connect.model.enums.ProjectApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectApplicationDTO {
    private Long id;
    private Long projectId;
    private BaseUserResponseDTO applicant;
    private LocalDate applicationDate;
    private ProjectApplicationStatus status;
    private boolean isMentorRequest;
}

