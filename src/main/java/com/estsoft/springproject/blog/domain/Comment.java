package com.estsoft.springproject.blog.domain;

import com.estsoft.springproject.blog.domain.DTO.CommentResponseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private String body;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Comment(String body, Article article) {
        this.body = body;
        this.article = article;
    }

    public void update(String body) {
        this.body = body;
    }

    public CommentResponseDTO convert() {
        return new CommentResponseDTO(id, article.getId(), body, createdAt);
    }
}