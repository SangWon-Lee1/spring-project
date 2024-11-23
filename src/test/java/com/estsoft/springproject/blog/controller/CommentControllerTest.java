package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.DTO.ArticleWithCommentsDTO;
import com.estsoft.springproject.blog.domain.DTO.CommentRequestDTO;
import com.estsoft.springproject.blog.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private Comment comment;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        Article article = new Article("Test Article", "Test Content");
        comment = new Comment("Test Comment", article);
    }

    @Test
    void saveCommentByArticleIdTest() throws Exception {
        // given
        when(commentService.saveComment(anyLong(), any(CommentRequestDTO.class))).thenReturn(comment);

        // when & then
        mockMvc.perform(post("/api/articles/{articleId}/comments", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"body\":\"Test Comment\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body", is("Test Comment")));
    }

    @Test
    void selectCommentByIdTest() throws Exception {
        // given
        when(commentService.findByComment(anyLong())).thenReturn(comment);

        // when & then
        mockMvc.perform(get("/api/comments/{commentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body", is("Test Comment")));
    }

    @Test
    void updateCommentByIdTest() throws Exception {
        // given
        Comment updatedComment = new Comment("Updated Comment", comment.getArticle());

        when(commentService.updateComment(anyLong(), any(CommentRequestDTO.class))).thenReturn(updatedComment);

        // when & then
        mockMvc.perform(put("/api/comment/{commentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"body\":\"Updated Comment\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body", is("Updated Comment")));
    }

    @Test
    void deleteCommentByIdTest() throws Exception {
        mockMvc.perform(delete("/api/comments/{commentId}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getArticleWithCommentsTest() throws Exception {
        // given
        Article article = new Article("Test Article", "Test Content");
        Comment comment1 = new Comment("Test Comment 1", article);
        Comment comment2 = new Comment("Test Comment 2", article);

        List<Comment> comments = List.of(comment1, comment2);
        ArticleWithCommentsDTO articleWithCommentsDTO = new ArticleWithCommentsDTO(article, comments);

        // when
        when(commentService.findArticleWithCommentsById(anyLong())).thenReturn(articleWithCommentsDTO);

        // then
        mockMvc.perform(get("/api/articles/{articleId}/comments", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Article")))
                .andExpect(jsonPath("$.comments.size()", is(2)))
                .andExpect(jsonPath("$.comments[0].body", is("Test Comment 1")))
                .andExpect(jsonPath("$.comments[1].body", is("Test Comment 2")));
    }
}
