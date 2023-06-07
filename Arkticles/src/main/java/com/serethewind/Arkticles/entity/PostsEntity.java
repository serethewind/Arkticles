package com.serethewind.Arkticles.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "posts")
public class PostsEntity {
    /**
     * Attributes: Title, Content, Author, Creation Date, Last Updated Date, Tags, etc.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 100)
    private String title;
    @Column(nullable = false)
    @Size(min = 1)
    private String content;
    //    private String author;
    //  private Comment comments;
    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentsEntity> comments = new ArrayList<>();
    @CreationTimestamp
    private LocalDateTime creationDate;
    @UpdateTimestamp
    private LocalDateTime lastUpdated;
    // private set of Tag tags;
}
