package com.estsoft.springproject.blog.repository;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Article article;

    @BeforeEach
    void setUp() {
        article = Article.builder()
                .title("Test Article")
                .content("Test Content")
                .build();
        blogRepository.save(article);

        Comment comment1 = new Comment("First comment", article);
        Comment comment2 = new Comment("Second comment", article);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
    }

    @Test
    void findByArticleIdTest() {
        // given
        Long articleId = article.getId();

        // when
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // then
        assertThat(comments.size(), is(2));
        assertThat(comments.get(0).getBody(), is("First comment"));
        assertThat(comments.get(1).getBody(), is("Second comment"));
    }

    @Test
    void findByArticleIdWhenNoCommentsTest() {
        // given
        Article newArticle = Article.builder()
                .title("No comments article")
                .content("No comments content")
                .build();
        blogRepository.save(newArticle);

        // when
        List<Comment> comments = commentRepository.findByArticleId(newArticle.getId());

        // then
        assertThat(comments.isEmpty(), is(true));
    }

    @Test
    void findByArticleIdWhenArticleNotExistTest() {
        // given
        Long invalidArticleId = 999L;

        // when
        List<Comment> comments = commentRepository.findByArticleId(invalidArticleId);

        // then
        assertThat(comments.isEmpty(), is(true));
    }
}