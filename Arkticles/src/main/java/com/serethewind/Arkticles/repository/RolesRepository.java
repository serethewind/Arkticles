package com.serethewind.Arkticles.repository;

import com.serethewind.Arkticles.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<RolesEntity, Long> {

    RolesEntity findByName(String name);
}
