package com.estsoft.springproject.blog.domain.DTO;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.util.DateFormatUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDTO {
    private Long commentId;
    private Long articleId;
    private String body;
    private String createdAt;
    private ArticleResponse article;

    public CommentResponseDTO(Comment comment) {
        Article articleFromComment = comment.getArticle();

        commentId = comment.getId();
        articleId = articleFromComment.getId();
        body = comment.getBody();
        createdAt = comment.getCreatedAt().format(DateFormatUtil.formatter);
        article = new ArticleResponse(comment.getArticle());
    }

    public CommentResponseDTO(Long commentId, Long articleId, String body, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.body = body;
        this.createdAt = String.valueOf(createdAt);
    }
}
