package com.serethewind.Arkticles.repository;

import com.serethewind.Arkticles.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {
}
