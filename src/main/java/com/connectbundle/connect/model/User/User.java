package com.connectbundle.connect.model.User;

import com.connectbundle.connect.model.*;
import com.connectbundle.connect.model.Publication.Publication;
import com.connectbundle.connect.model.Publication.ResearchProposal;
import com.connectbundle.connect.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;
    private String profilePicture;
    private String headline;
    private String about;
    private String location;
    private String industry;
    private String currentPosition;

    @Enumerated(EnumType.STRING)
    private Role role; // Default STUDENT, FACULTY, ADMIN

    private boolean isActive;

    @Embedded
    private SocialLinks socialLinks;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<ProjectTeamMember> projectTeamMembers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSkill> userSkills;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Publication> publications ;


    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResearchProposal> researchProposals;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Awards> awards;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMember> clubMemberships;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfficeBearer> officeBearerRoles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventPerson> eventRoles = new ArrayList<>();

    @Embedded
    private StudentDetails studentDetails;

    @Embedded
    private FacultyDetails facultyDetails;

    @ElementCollection
    private List<String> achievement;

    @ElementCollection
    private List<String> interest;


    @PrePersist
    public void setDefaults() {
        if (this.role == null) {
            this.role = Role.STUDENT;
        }
        this.isActive = true;

    }
}
