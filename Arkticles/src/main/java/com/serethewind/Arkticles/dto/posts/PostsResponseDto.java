package com.serethewind.Arkticles.dto.posts;

import com.serethewind.Arkticles.entity.CommentsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private List<CommentsEntity> comments;
}
