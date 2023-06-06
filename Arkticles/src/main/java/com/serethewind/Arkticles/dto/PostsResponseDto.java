package com.serethewind.Arkticles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
}
