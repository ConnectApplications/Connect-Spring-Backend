package com.connectbundle.connect.model;

import com.connectbundle.connect.model.enums.EventType;
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
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private EventType type;

    private String location;

    private String registrationLink;

    private String natureOfEvent;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventPerson> eventTeam = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "event_themes", joinColumns = @JoinColumn(name = "event_id"))
    private List<String> theme = new ArrayList<>();

    private String fundingAgency;

    private String chiefGuest;

    @ElementCollection
    @CollectionTable(name = "event_speakers", joinColumns = @JoinColumn(name = "event_id"))
    private List<String> otherSpeakers = new ArrayList<>();

    private Integer participantsCount;

    private Boolean isCompleted = false;

    private Boolean isPublic = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
