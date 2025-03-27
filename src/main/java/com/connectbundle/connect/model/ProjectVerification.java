package com.connectbundle.connect.model;

import java.time.LocalDate;

import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.model.enums.ProjectVerificationStatus;

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
@Table(name = "project_verifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "verified_by", referencedColumnName = "id", nullable = false)
    private User verifiedBy;

    @Enumerated(EnumType.STRING)
    private ProjectVerificationStatus verificationStatus;

    @Column
    private LocalDate verificationDate;
}
