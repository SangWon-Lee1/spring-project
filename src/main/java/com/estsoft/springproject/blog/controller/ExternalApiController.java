package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.DTO.AddArticleRequest;
import com.estsoft.springproject.blog.domain.DTO.ArticleResponse;
import com.estsoft.springproject.blog.domain.DTO.CommentRequestDTO;
import com.estsoft.springproject.blog.domain.DTO.CommentResponseDTO;
import com.estsoft.springproject.blog.service.BlogService;
import com.estsoft.springproject.blog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExternalApiController {
    private static final Logger log = LoggerFactory.getLogger(ExternalApiController.class);

    private final BlogService blogService;
    private final CommentService commentService;

    public ExternalApiController(BlogService blogService, CommentService commentService) {
        this.blogService = blogService;
        this.commentService = commentService;
    }

    @GetMapping("/api/external")
    public String callApi() {
        // 외부 API 호출
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/posts";
        // 외부 API 호출, 역직렬화 (restTemplate)
        ResponseEntity<List<Content>> resultList =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        List<Content> contents = resultList.getBody();

        if (contents != null) {
            for (Content content : contents) {
                AddArticleRequest request = new AddArticleRequest(content.getTitle(), content.getBody());
                Article article = blogService.saveArticle(request);
                log.info("saved article: {}", article.convert());
            }
        }

        log.info("Code: {}", resultList.getStatusCode());
        log.info("{}", resultList.getBody());
        return "ok";
    }

    @GetMapping("/api/external/articles")
    public ResponseEntity<List<ArticleResponse>> createArticlesFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/posts";
        ResponseEntity<List<Content>> resultList = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        List<Content> contents;
        if (resultList.getBody() != null) {
            contents = resultList.getBody();
        } else {
            contents = new ArrayList<>();
        }

        List<ArticleResponse> requests = contents.stream()
                .map(content -> blogService.saveArticle(new AddArticleRequest(content.getTitle(), content.getBody())).convert())
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(requests);
    }

    @GetMapping("/api/external/comments")
    public ResponseEntity<List<CommentResponseDTO>> createCommentsFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/comments";
        ResponseEntity<List<CommentsContent>> resultList = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        List<CommentsContent> contents;
        if (resultList.getBody() != null) {
            contents = resultList.getBody();
        } else {
            contents = new ArrayList<>();
        }

        List<CommentResponseDTO> requests = contents.stream()
                .map(content -> commentService.saveComment(content.getPostId(), new CommentRequestDTO(content.getBody())).convert())
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(requests);
    }
}