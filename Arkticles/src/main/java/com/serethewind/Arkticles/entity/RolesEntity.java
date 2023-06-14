package com.serethewind.Arkticles.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role_list")
public class RolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
