package com.connectbundle.connect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "club_events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_id", referencedColumnName = "id", nullable = false)
    private Club club;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(columnDefinition = "TEXT")
    private String outcomes;

    @Column(columnDefinition = "TEXT")
    private String awards;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;

} 