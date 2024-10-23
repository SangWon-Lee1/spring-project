package com.estsoft.springproject.blog.service;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.DTO.ArticleWithCommentsDTO;
import com.estsoft.springproject.blog.domain.DTO.CommentRequestDTO;
import com.estsoft.springproject.blog.repository.BlogRepository;
import com.estsoft.springproject.blog.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    public CommentService(CommentRepository commentRepository ,BlogRepository blogRepository) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
    }

    public Comment saveComment(Long articleId, CommentRequestDTO commentRequestDTO) {
        Article article = blogRepository.findById(articleId)
                .orElseThrow();
        return commentRepository.save(new Comment(commentRequestDTO.getBody(), article));
    }

    public Comment findByComment(Long commentId) {
        Optional<Comment> comment =  commentRepository.findById(commentId);
        return comment.orElse(new Comment());
    }

    public Comment updateComment(Long commentId, CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
//        Comment comment = findByComment(commentId);
        comment.update(commentRequestDTO.getBody());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public ArticleWithCommentsDTO findArticleWithCommentsById(Long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow();
        List<Comment> comments = commentRepository.findByArticleId(id);
        return new ArticleWithCommentsDTO(article, comments);
    }
}
