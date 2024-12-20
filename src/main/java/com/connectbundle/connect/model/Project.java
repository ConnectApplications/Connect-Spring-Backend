package com.connectbundle.connect.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(nullable = false,length=1000)
    private String projectDescription;

    @Column(nullable = false)
    private String projectOwner;

    @Column(nullable = false)
    private String projectLevel;

    @Column(nullable = false)
    private Boolean projectVerified;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<ProjectMember> projectMembers;

    @ElementCollection
    @CollectionTable(name = "project_tags", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "tag")
    private List<String> projectTags;

    @Column(nullable = false)
    private String projectStatus;

    private String projectImage;

    @ElementCollection
    @CollectionTable(name = "project_tech_stack", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "tech")
    private List<String> projectTechStack;

    @ElementCollection
    @CollectionTable(name = "project_prerequisites", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "prerequisite")
    private List<String> projectPreRequisites;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<ProjectMilestone> projectMilestones;

    private String projectRepo;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private User faculty;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
