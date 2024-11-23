package com.estsoft.springproject.blog.domain;

import com.estsoft.springproject.blog.domain.DTO.CommentResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    private Comment comment;
    private Article article;

    @BeforeEach
    void setUp() {
        article = new Article("Test Article", "test content.");
        comment = new Comment("test comment", article);
    }

    @Test
    void testCommentCreation() {
        assertEquals("test comment", comment.getBody());
        assertEquals(article, comment.getArticle());
        assertNotNull(comment.getCreatedAt());
    }

    @Test
    void testUpdateComment() {
        comment.update("Updated comment");

        assertEquals("Updated comment", comment.getBody());
    }

    @Test
    void testConvertToDTO() {
        CommentResponseDTO dto = comment.convert();
        String expectedCreatedAt = comment.getCreatedAt().toString();

        assertEquals(comment.getId(), dto.getCommentId());
        assertEquals(comment.getArticle().getId(), dto.getArticleId());
        assertEquals(comment.getBody(), dto.getBody());
        assertEquals(expectedCreatedAt, dto.getCreatedAt());
    }
}