package com.connectbundle.connect.model;

import com.connectbundle.connect.model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;  // The user who follows

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User following; // The user being followed

    private LocalDateTime followedAt = LocalDateTime.now(); // Timestamp
}
