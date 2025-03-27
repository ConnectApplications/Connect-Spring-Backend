package com.connectbundle.connect.model.User;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultyDetails {

    private String employeeId;
    private String department;
    private String designation;
    private String specialization;
    private String qualification;


}
