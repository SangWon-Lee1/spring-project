package com.estsoft.springproject.blog.domain.DTO;

import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.util.DateFormatUtil;
import lombok.Getter;

@Getter
public class CommentSummaryDTO {
    private Long commentId;
    private Long articleId;
    private String body;
    private String createdAt;

    public CommentSummaryDTO(Comment comment) {
        this.commentId = comment.getId();
        this.articleId = comment.getArticle().getId();
        this.body = comment.getBody();
        this.createdAt = comment.getCreatedAt().format(DateFormatUtil.formatter);
    }
}
