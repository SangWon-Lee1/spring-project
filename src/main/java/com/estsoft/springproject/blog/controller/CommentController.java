package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.DTO.ArticleWithCommentsDTO;
import com.estsoft.springproject.blog.domain.DTO.CommentRequestDTO;
import com.estsoft.springproject.blog.domain.DTO.CommentResponseDTO;
import com.estsoft.springproject.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentResponseDTO> saveCommentByArticleId(@PathVariable Long articleId, @RequestBody CommentRequestDTO commentRequestDTO) {
        Comment comment = commentService.saveComment(articleId, commentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommentResponseDTO(comment));
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> selectCommentById(@PathVariable("commentId") Long id) {
        Comment comment = commentService.findByComment(id);
        return ResponseEntity.ok(new CommentResponseDTO(comment));
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateCommentById(@PathVariable Long commentId, @RequestBody CommentRequestDTO commentRequestDTO) {
        Comment comment = commentService.updateComment(commentId, commentRequestDTO);
        return ResponseEntity.ok(new CommentResponseDTO(comment));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<ArticleWithCommentsDTO> getArticleWithComments(@PathVariable Long articleId) {
        ArticleWithCommentsDTO articleWithCommentsDTO = commentService.findArticleWithCommentsById(articleId);
        return ResponseEntity.ok(articleWithCommentsDTO);
    }
}
