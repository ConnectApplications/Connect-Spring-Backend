package com.connectbundle.connect.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ProjectMilestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String milestoneName;

    @Column(nullable = false)
    private LocalDate expectedDate;

    @Column(nullable = false)
    private boolean completed;

    public ProjectMilestone() {}

    public ProjectMilestone(String milestoneName, LocalDate expectedDate, boolean completed) {
        this.milestoneName = milestoneName;
        this.expectedDate = expectedDate;
        this.completed = completed;
    }
}
