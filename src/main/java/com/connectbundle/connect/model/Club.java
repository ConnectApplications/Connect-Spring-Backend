package com.connectbundle.connect.model;

import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.model.enums.ClubJoinPolicy;
import com.connectbundle.connect.model.enums.ClubVisibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clubs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String banner;

    private String logo;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String department;

    @ElementCollection
    @CollectionTable(name = "club_tags", joinColumns = @JoinColumn(name = "club_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String otherDetails;

    @Enumerated(EnumType.STRING)
    private ClubVisibility visibility = ClubVisibility.PUBLIC;

    @Enumerated(EnumType.STRING)
    private ClubJoinPolicy joinPolicy = ClubJoinPolicy.OPEN;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMember> members = new ArrayList<>();

//    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Post> activityFeed = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Achievement> achievements = new ArrayList<>();

    @Embedded
    private PlanOfAction planOfAction;

    @ManyToOne
    @JoinColumn(name = "advisor_id", referencedColumnName = "id", nullable = true)
    private User advisor;

    private boolean isActive = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
