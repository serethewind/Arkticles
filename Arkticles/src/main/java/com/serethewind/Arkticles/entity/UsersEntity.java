package com.serethewind.Arkticles.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
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

//    @JsonManagedReference
    @OneToMany(mappedBy = "userAuthor", cascade = CascadeType.ALL)
    private List<PostsEntity> posts;


    @OneToMany(mappedBy = "userAuthor", cascade = CascadeType.ALL)
    private List<CommentsEntity> comments;

    @CreationTimestamp
    private LocalDateTime dateRegistered;
}
