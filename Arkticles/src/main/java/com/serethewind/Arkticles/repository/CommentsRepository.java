package com.serethewind.Arkticles.repository;

import com.serethewind.Arkticles.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {
    @Query("select c from comments c where c.posts.id = ?1")
    List<CommentsEntity> findCommentByPostID(Long id);

    @Query("select (count(c) > 0) from comments c where c.posts.id = ?1")
    Boolean isCommentByPost(Long id);




//    List<CommentsEntity> findByPosts_Id(Long id);


}
