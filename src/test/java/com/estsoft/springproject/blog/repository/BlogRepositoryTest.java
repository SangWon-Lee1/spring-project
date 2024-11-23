package com.estsoft.springproject.blog.repository;

import com.estsoft.springproject.blog.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
class BlogRepositoryTest {
    @Autowired
    private BlogRepository blogRepository;

    @BeforeEach
    void cleanDatabase() {
        blogRepository.deleteAll(); // 모든 데이터를 삭제
    }

    @Test
    void saveTest() {
        // given
        Article article = new Article("Test Title", "Test Content");

        // when
        Article savedArticle = blogRepository.save(article);

        // then
        assertThat(savedArticle.getId() != null, is(true));
        assertThat(savedArticle.getTitle(), is("Test Title"));
        assertThat(savedArticle.getContent(), is("Test Content"));
    }

    @Test
    void findByIdTest() {
        // given
        Article article = new Article("Test Title", "Test Content");
        Article savedArticle = blogRepository.save(article);

        // when
        Optional<Article> foundArticle = blogRepository.findById(savedArticle.getId());

        // then
        assertThat(foundArticle.isPresent(), is(true));
        assertThat(foundArticle.get().getTitle(), is("Test Title"));
        assertThat(foundArticle.get().getContent(), is("Test Content"));
    }

    @Test
    void findAllTest() {
        // given
        Article article1 = new Article("Title1", "Content1");
        Article article2 = new Article("Title2", "Content2");
        blogRepository.save(article1);
        blogRepository.save(article2);

        // when
        List<Article> articles = blogRepository.findAll();

        // then
        assertThat(articles.size(), is(2));
    }

    @Test
    void deleteByIdTest() {
        // given
        Article article = new Article("Test Title", "Test Content");
        Article savedArticle = blogRepository.save(article);

        // when
        blogRepository.deleteById(savedArticle.getId());

        // then
        Optional<Article> deletedArticle = blogRepository.findById(savedArticle.getId());
        assertThat(deletedArticle.isPresent(), is(false));
    }
}
