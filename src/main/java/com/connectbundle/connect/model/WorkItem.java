package com.connectbundle.connect.model;

import com.connectbundle.connect.model.enums.WorkItemLevel;
import com.connectbundle.connect.model.enums.WorkItemStatus;
import com.connectbundle.connect.model.enums.WorkItemType;
import com.connectbundle.connect.model.enums.WorkItemVerification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private WorkItemType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private WorkItemStatus status;

    @Column(nullable = false)
    private WorkItemLevel level;

    @Column(nullable = false)
    private WorkItemVerification verified;

    @ManyToOne
    @JoinColumn(name = "faculty_assigned", referencedColumnName = "id", nullable = false)
    private User user;

    @Column()
    private String company;

    @Column()
    private String duration;

    @Column()
    private String activity;

    @Column()
    private String techStack;

    @Column()
    private String collaborators;

    @Column()
    private String achievement;

    @Column()
    private String category;

}
