package com.connectbundle.connect.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
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

    public Project() {
    }

    public Project(String projectName, String projectDescription, String projectOwner,
                   List<ProjectMember> projectMembers, List<String> projectTags, String projectStatus,
                   String projectImage, List<String> projectTechStack, List<String> projectPreRequisites,
                   List<ProjectMilestone> projectMilestones, String projectRepo) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectOwner = projectOwner;
        this.projectMembers = projectMembers;
        this.projectTags = projectTags;
        this.projectStatus = projectStatus;
        this.projectImage = projectImage;
        this.projectTechStack = projectTechStack;
        this.projectPreRequisites = projectPreRequisites;
        this.projectMilestones = projectMilestones;
        this.projectRepo = projectRepo;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public List<ProjectMember> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(List<ProjectMember> projectMembers) {
        this.projectMembers = projectMembers;
    }

    public List<String> getProjectTags() {
        return projectTags;
    }

    public void setProjectTags(List<String> projectTags) {
        this.projectTags = projectTags;
    }

    public String getProjectImage() {
        return projectImage;
    }

    public void setProjectImage(String projectImage) {
        this.projectImage = projectImage;
    }

    public List<String> getProjectTechStack() {
        return projectTechStack;
    }

    public void setProjectTechStack(List<String> projectTechStack) {
        this.projectTechStack = projectTechStack;
    }

    public List<String> getProjectPreRequisites() {
        return projectPreRequisites;
    }

    public void setProjectPreRequisites(List<String> projectPreRequisites) {
        this.projectPreRequisites = projectPreRequisites;
    }

    public List<ProjectMilestone> getProjectMilestones() {
        return projectMilestones;
    }

    public void setProjectMilestones(List<ProjectMilestone> projectMilestones) {
        this.projectMilestones = projectMilestones;
    }

    public String getProjectRepo() {
        return projectRepo;
    }

    public void setProjectRepo(String projectRepo) {
        this.projectRepo = projectRepo;
    }
}
