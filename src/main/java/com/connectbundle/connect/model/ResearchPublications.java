package com.connectbundle.connect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "research_publications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResearchPublications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String authors;

    @Column(nullable = false)
    private String journalName;

    @Column(nullable = false)
    private Integer publicationYear;

    @Column(unique = true, nullable = false)
    private String doi;
}