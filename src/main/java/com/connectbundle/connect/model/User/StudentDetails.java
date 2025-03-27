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
public class StudentDetails {

    private String enrollmentNumber;
    private String branch;
    private String course;
    private String college;
    private String semester;
    private String graduationYear;
    private String section;


}
