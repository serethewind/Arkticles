package com.serethewind.Arkticles.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "_users")
public class UsersEntity {

    /**
     * Users:
     *
     * Entity: User
     * Attributes: Username, Email, Password, Name, Profile Picture, etc.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fullname;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    @Email
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "userAuthor", cascade = CascadeType.ALL)
    private List<PostsEntity> posts;
    @CreationTimestamp
    private LocalDateTime dateRegistered;
}
