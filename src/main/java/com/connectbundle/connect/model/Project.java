package com.connectbundle.connect.model;

import java.util.List;

import com.connectbundle.connect.model.enums.ProjectLevelEnum;
import com.connectbundle.connect.model.enums.ProjectStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String projectName;

    @Column(columnDefinition = "TEXT")
    private String projectDescription;

    @Column(columnDefinition = "TEXT")
    private String prerequisites;

    @ManyToOne
    @JoinColumn(name = "facultyMentorID", referencedColumnName = "id", nullable = false)
    private User facultyMentor;

    @ManyToOne
    @JoinColumn(name = "verificationFacultyID", referencedColumnName = "id", nullable = false)
    private User verificationFaculty;

    @Column(columnDefinition = "TEXT")
    private String techStack;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column
    private Integer projectDurationMonths;

    @Enumerated(EnumType.STRING)
    private ProjectLevelEnum projectLevel;

    @Enumerated(EnumType.STRING)
    private ProjectStatusEnum projectStatus;

    private Integer maxTeamSize;

    private String projectImage;

    private String projectRepo;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectApplication> projectApplications;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTeamMember> projectTeamMembers;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectVerification> projectVerifications;
}
