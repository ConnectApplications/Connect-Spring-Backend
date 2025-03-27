package com.connectbundle.connect.model;

import com.connectbundle.connect.model.User.User;
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
@Table(name = "awards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Awards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recepient_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String award_name;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

}
