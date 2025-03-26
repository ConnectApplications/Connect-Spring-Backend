package com.connectbundle.connect.model;

import java.util.List;

import com.connectbundle.connect.model.enums.Role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String firstName;
    private String lastName;
    private String profilePicture;
    private String headline;
    private String about;
    private String location;
    private String industry;
    private String currentPosition;

    @Enumerated(EnumType.STRING)
    private Role role; // Default STUDENT, FACULTY, ADMIN

    private boolean isActive;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<ProjectTeamMember> projectTeamMembers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSkill> userSkills;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Student_Details studentDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Faculty_Details facultyDetails;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BooksPublished> booksPublished;

    @OneToMany(mappedBy = "submittedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResearchProposals> researchProposals;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Awards> awards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> eventsOrganized;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMember> clubMemberships;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfficeBearer> officeBearerRoles;

    @PrePersist
    public void setDefaults() {
        if (this.role == null) {
            this.role = Role.STUDENT;
        }

    }
}
