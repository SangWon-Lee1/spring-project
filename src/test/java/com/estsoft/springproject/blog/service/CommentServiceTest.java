package com.estsoft.springproject.blog.service;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.DTO.ArticleWithCommentsDTO;
import com.estsoft.springproject.blog.domain.DTO.CommentRequestDTO;
import com.estsoft.springproject.blog.repository.BlogRepository;
import com.estsoft.springproject.blog.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BlogRepository blogRepository;

    private Article article;
    private Comment comment;
    private CommentRequestDTO commentRequestDTO;

    @BeforeEach
    void setUp() {
        article = new Article("Test Article", "Test Content");
        comment = new Comment("Test Comment", article);
        commentRequestDTO = new CommentRequestDTO("Test Comment");
    }

    @Test
    void saveCommentTest() {
        // given
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(article));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // when
        Comment savedComment = commentService.saveComment(1L, commentRequestDTO);

        // then
        assertNotNull(savedComment);
        assertEquals("Test Comment", savedComment.getBody());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void findByCommentTest() {
        // given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        // when
        Comment foundComment = commentService.findByComment(1L);

        // then
        assertNotNull(foundComment);
        assertEquals("Test Comment", foundComment.getBody());
    }

    @Test
    void updateCommentTest() {
        // given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        CommentRequestDTO updatedCommentRequestDTO = new CommentRequestDTO("Updated Comment");

        // Mock: save 메소드가 comment를 제대로 저장하고 반환하도록 설정
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment commentToSave = invocation.getArgument(0);
            commentToSave.update(updatedCommentRequestDTO.getBody());
            return commentToSave;
        });

        // when
        Comment updatedComment = commentService.updateComment(1L, updatedCommentRequestDTO);

        // then
        assertNotNull(updatedComment);
        assertEquals("Updated Comment", updatedComment.getBody());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void deleteCommentTest() {
        // when
        commentService.deleteComment(1L);

        // then
        verify(commentRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void findArticleWithCommentsByIdTest() {
        // given
        Comment comment1 = new Comment("Comment 1", article);
        Comment comment2 = new Comment("Comment 2", article);
        List<Comment> comments = List.of(comment1, comment2);

        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(article));
        when(commentRepository.findByArticleId(anyLong())).thenReturn(comments);

        // when
        ArticleWithCommentsDTO result = commentService.findArticleWithCommentsById(1L);

        // then
        assertNotNull(result);
        assertEquals("Test Article", result.getTitle());
        assertEquals("Test Content", result.getContent());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertEquals(2, result.getComments().size());
        assertEquals("Comment 1", result.getComments().get(0).getBody());
        assertEquals("Comment 2", result.getComments().get(1).getBody());
    }
}