package com.connectbundle.connect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "achievements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime date;

}
