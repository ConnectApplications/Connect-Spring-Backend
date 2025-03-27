package com.connectbundle.connect.model;

import java.util.List;
import java.time.LocalDateTime;

import com.connectbundle.connect.model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "club")
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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String department;

    @Column(columnDefinition = "TEXT")
    private String otherDetails;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfficeBearer> officeBearers;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMember> clubMembers;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubEvent> events;

    @Embedded
    private PlanOfAction planOfAction;

    @ManyToOne
    @JoinColumn(name = "advisor_id", referencedColumnName = "id", nullable = true)
    private User advisor;

    @ManyToOne
    @JoinColumn(name = "club_head_id", referencedColumnName = "id", nullable = false)
    private User clubHead;

    @Column(nullable = false)
    private int members_count;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.members_count = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
