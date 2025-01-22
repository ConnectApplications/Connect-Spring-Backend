package com.connectbundle.connect.model;

import java.time.LocalDate;

import com.connectbundle.connect.model.enums.ResearchProposalStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "research_proposals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResearchProposals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "submitted_by", referencedColumnName = "id", nullable = false)
    private User submittedBy; // Foreign key to users

    @Column(nullable = false)
    private LocalDate submissionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResearchProposalStatus status; // SUBMITTED, REVIEWED, APPROVED, REJECTED

    @Column(nullable = false)
    private Double fundingAmount;
}
