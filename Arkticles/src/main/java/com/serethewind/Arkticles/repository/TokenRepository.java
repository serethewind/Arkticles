package com.serethewind.Arkticles.repository;

import com.serethewind.Arkticles.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    @Query("select _ from _tokens _ where _.user.id = ?1 and _.expired = false and _.revoked = false")
    List<TokenEntity> findAllValidTokensByUser(Long id);

    Optional<TokenEntity> findByToken(String token);


}
