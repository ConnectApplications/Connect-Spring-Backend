package com.connectbundle.connect.model.User;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


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

    private Double totalPerformanceScore;
    private String thresholdLevel;
    private Long capacity;
    private Double currentProjectPoints;
    private Double easyCommitmentScore;
    private Double mediumCommitmentScore;

    private Double difficultCommitmentScore;


    @ElementCollection
    private Map<String, Integer> skillPoints;
}
