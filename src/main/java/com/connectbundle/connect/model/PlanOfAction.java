package com.connectbundle.connect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanOfAction {
    
    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private double budget;
} 