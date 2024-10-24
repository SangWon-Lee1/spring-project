package com.estsoft.springproject.blog.service;

import com.estsoft.springproject.blog.domain.DTO.AddArticleRequest;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.DTO.UpdateArticleRequest;
import com.estsoft.springproject.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogService {
    // 의존성주입(DI)
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    // blog 게시글 저장
    public Article saveArticle(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    // blog 게시글 조회
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // blog 게시글 단건 조회
    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found id: " + id));
    }

    // blog 삭제
    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = findById(id);    // 수정하고싶은 row (article객체) 가져옴
        article.update(request.getTitle(), request.getContent());
        return article;
    }
}