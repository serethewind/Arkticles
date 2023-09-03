package com.serethewind.Arkticles.repository;

import com.serethewind.Arkticles.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersEntity, Long> {
    @Query("select _ from _users _ where upper(_.username) = upper(?1)")
    Optional<UsersEntity> findUserByUsername(String username);

    @Query("select (count(_) > 0) from _users _ where upper(_.username) = upper(?1) or upper(_.email) = upper(?2)")
    Boolean existsByUsernameOrEmail(String username, String email);


}
