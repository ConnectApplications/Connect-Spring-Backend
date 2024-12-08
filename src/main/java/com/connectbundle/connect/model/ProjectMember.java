package com.connectbundle.connect.model;

import jakarta.persistence.*;

@Entity
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberRollNo;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String memberRole;


    public ProjectMember() {}

    public ProjectMember(String memberRollNo, String memberName, String memberRole) {
        this.memberRollNo = memberRollNo;
        this.memberName = memberName;
        this.memberRole = memberRole;
    }
}
