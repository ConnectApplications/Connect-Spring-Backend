package com.connectbundle.connect.model;

import com.connectbundle.connect.model.enums.PostTypeEnum;
import com.connectbundle.connect.model.enums.PostVisibilityEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name="author_id",referencedColumnName = "id",nullable = false)
    private User author;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private PostTypeEnum type = PostTypeEnum.ALL;

    @Column
    @Enumerated(EnumType.STRING)
    private PostVisibilityEnum visibility = PostVisibilityEnum.PUBLIC;

    @Column
    private String[] tags;
    @Column
    private String media;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    public int getLikesCount() {
        return likes != null ? likes.size() : 0;
    }

    public int getCommentsCount() {
        return comments != null ? comments.size() : 0;
    }

    @PrePersist
    public void setDefaults() {
        if (this.visibility == null) {
            this.visibility = PostVisibilityEnum.PUBLIC;
        }
        if (this.type == null) {
            this.type = PostTypeEnum.ALL;
        }
    }
}
