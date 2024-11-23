package com.estsoft.springproject.blog.service;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.DTO.AddArticleRequest;
import com.estsoft.springproject.blog.domain.DTO.UpdateArticleRequest;
import com.estsoft.springproject.blog.repository.BlogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @InjectMocks
    private BlogService service;

    @Mock
    private BlogRepository repository;

    @Test
    void saveArticleTest() {
        // given
        AddArticleRequest request = new AddArticleRequest("Test Title", "Test Content");
        Article mockArticle = request.toEntity();

        when(repository.save(any(Article.class))).thenReturn(mockArticle);

        // when
        Article savedArticle = service.saveArticle(request);

        // then
        assertThat(savedArticle.getTitle(), is("Test Title"));
        assertThat(savedArticle.getContent(), is("Test Content"));
        verify(repository, times(1)).save(any(Article.class));
    }

    @Test
    void findAllTest() {
        // given
        Article article1 = new Article("Title1", "Content1");
        Article article2 = new Article("Title2", "Content2");

        when(repository.findAll()).thenReturn(List.of(article1, article2));

        // when
        List<Article> articles = service.findAll();

        // then
        assertThat(articles.size(), is(2));
        assertThat(articles.get(0).getTitle(), is("Title1"));
        verify(repository, times(1)).findAll();
    }

    @Test
    void findByIdTest() {
        // given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.findById(1L));
        assertThat(exception.getMessage(), is("not found id: 1"));
    }

    @Test
    void deleteByIdTest() {
        // when
        service.deleteById(1L);

        // then
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void updateTest() {
        // given
        Article mockArticle = new Article("Old Title", "Old Content");
        UpdateArticleRequest request = new UpdateArticleRequest("Updated Title", "Updated Content");

        when(repository.findById(1L)).thenReturn(Optional.of(mockArticle));

        // when
        Article updatedArticle = service.update(1L, request);

        // then
        assertThat(updatedArticle.getTitle(), is("Updated Title"));
        assertThat(updatedArticle.getContent(), is("Updated Content"));
        verify(repository, times(1)).findById(1L);
    }
}
