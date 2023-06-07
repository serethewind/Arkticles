package com.serethewind.Arkticles.controller;

import com.serethewind.Arkticles.dto.comments.CommentsRequestDto;
import com.serethewind.Arkticles.dto.comments.CommentsResponseDto;
import com.serethewind.Arkticles.service.comments.serviceImpl.CommentsServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/arkticles/v1/comments")
@AllArgsConstructor
public class CommentsController {

    private CommentsServiceImplementation commentsService;

    @PostMapping
    public ResponseEntity<CommentsResponseDto> createComment(@RequestBody CommentsRequestDto commentsRequestDto){
        return ResponseEntity.ok(commentsService.createComment(commentsRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentsResponseDto> updateComment(@PathVariable("id") Long id, @RequestBody CommentsRequestDto commentsRequestDto){
        return  new ResponseEntity<>(commentsService.updateComment(id, commentsRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id){
        return ResponseEntity.ok(commentsService.deleteComment(id));
    }
}
