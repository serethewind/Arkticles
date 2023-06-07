package com.serethewind.Arkticles.repository;

import com.serethewind.Arkticles.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersEntity, Long> {
}
