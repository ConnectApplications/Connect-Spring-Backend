package com.connectbundle.connect.model;

import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.model.enums.ProjectApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "project_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id", nullable = false)
    private User applicant;

    @Column(nullable = false)
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    private ProjectApplicationStatus status;

    @Column(nullable = false)
    private boolean isMentorRequest = false;

    @Column(length = 500)
    private String description;



}
