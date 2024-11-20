package com.estsoft.springproject.blog.domain.DTO;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.util.DateFormatUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "블로그 조회 결과")
public class ArticleResponse {
    @Schema(description = "블로그 ID", type = "Long")
    private Long id;
    @Schema(description = "블로그 제목", type = "String")
    private String title;
    @Schema(description = "블로그 내용", type = "String")
    private String content;
    private String createdAt;
    private String updatedAt;

    public ArticleResponse(Article article) {
        id = article.getId();
        title = article.getTitle();
        content = article.getContent();
        createdAt = article.getCreatedAt() != null ? article.getCreatedAt().format(DateFormatUtil.formatter) : "";
        updatedAt = article.getUpdatedAt() != null ? article.getUpdatedAt().format(DateFormatUtil.formatter) : "";
    }

    public ArticleResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
