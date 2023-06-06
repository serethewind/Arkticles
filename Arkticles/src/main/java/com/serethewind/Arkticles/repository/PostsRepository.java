package com.serethewind.Arkticles.repository;

import com.serethewind.Arkticles.entity.PostsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<PostsEntity, Long> {
}
