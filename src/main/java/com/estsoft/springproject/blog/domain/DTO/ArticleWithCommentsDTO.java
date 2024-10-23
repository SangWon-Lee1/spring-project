package com.estsoft.springproject.blog.domain.DTO;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.util.DateFormatUtil;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleWithCommentsDTO {
    private final Long articleId;
    private final String title;
    private final String content;
    private final String createdAt;
    private final String updatedAt;
    private final List<CommentSummaryDTO> comments;

    public ArticleWithCommentsDTO(Article article, List<Comment> comments) {
        this.articleId = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt().format(DateFormatUtil.formatter);
        this.updatedAt = article.getUpdatedAt().format(DateFormatUtil.formatter);
        this.comments = comments.stream().map(CommentSummaryDTO::new).toList();
    }
}
