package com.connectbundle.connect.model;

import com.connectbundle.connect.model.enums.PostType;
import com.connectbundle.connect.model.enums.PostVisibility;

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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private PostVisibility visibility;

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = false)
    private Integer likes;

    @Column()
    private Integer comments;

    @Column()
    private Integer reposts;

    @Column
    private PostType type;

}
