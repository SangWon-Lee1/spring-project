package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.DTO.AddArticleRequest;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.DTO.UpdateArticleRequest;
import com.estsoft.springproject.blog.repository.BlogRepository;
import com.estsoft.springproject.blog.service.BlogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogRepository repository;

    @Autowired
    private BlogService blogService;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        repository.deleteAll();
    }

    // POST / articles API 테스트
    @Test
    public void addArticle() throws Exception {
        // given : article 저장
        AddArticleRequest request = new AddArticleRequest("제목", "내용");
        // 직렬화 (객체 -> JSON)
        String json = objectMapper.writeValueAsString(request);

        // when : POST /articles 호출
        ResultActions resultActions = mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // then : 호출 결과 검증
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("content").value(request.getContent()));

        List<Article> articleList = repository.findAll();
        Assertions.assertThat(articleList.size()).isEqualTo(1);
    }

    // 블로글 게시글 조회 API
    @Test
    public void findAll() throws Exception {
        // given : 조회 API에 필요한 값 셋팅
        Article article = repository.save(new Article("title", "content"));

        // when : 조회 API
        ResultActions resultActions = mockMvc.perform(get("/articles")
                .accept(MediaType.APPLICATION_JSON));

        // then : API 호출
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(article.getTitle()))
                .andExpect(jsonPath("$[0].content").value(article.getContent()));
    }

    // 블로그 단건 조회 API
    @Test
    public void findOne() throws Exception {
        // given : data insert
        Article article = repository.save(new Article("title", "content"));

        // when : API 호출
        ResultActions resultActions = mockMvc.perform(get("/articles/{id}", article.getId())
                .accept(MediaType.APPLICATION_JSON));

        // then : API 호출 결과 검증
        // -> given절에서 추가한 데이터가 그대로 JSON형태로 넘어오는지
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(article.getId()))
                .andExpect(jsonPath("$.title").value(article.getTitle()))
                .andExpect(jsonPath("$.content").value(article.getContent()));
    }

    // 블로그 단건 조회 API - id에 해당하는 자원이 없을 경우 (4xx) 예외처리 검증
    @Test
    public void findOneException() throws Exception {

        // when : API 호출
        ResultActions resultActions = mockMvc.perform(get("/articles/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON));

        // then : Exception 검증, Status code 검증
        resultActions.andExpect(status().isBadRequest());

        assertThrows(IllegalArgumentException.class, () -> blogService.findById(1L));
    }

    // 블로그 단건 삭제 API
    @Test
    public void deleteOne() throws Exception {
        // give
        Article article = repository.save(new Article("title", "content"));

        // when
        ResultActions resultActions = mockMvc.perform(delete("/articles/{id}", article.getId()));

        // then
        resultActions.andExpect(status().isOk());
        List<Article> articleList = repository.findAll();
        Assertions.assertThat(articleList).isEmpty();
    }

    // 블로그 수정 API
    // PUT /articles/{id} body(json content)요청
    @Test
    public void updateArticle() throws Exception {
        Article article = repository.save(new Article("title", "content"));
        UpdateArticleRequest request = new UpdateArticleRequest("변경 제목", "변경 내용");
        String updateJsonContent = objectMapper.writeValueAsString(request);

        // 수정 데이터(object) -> JSON
        ResultActions resultActions = mockMvc.perform(put("/articles/{id}", article.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJsonContent)
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(request.getTitle()));
    }

    // 블로그 수정 API 실패시
    @Test
    public void updateException() throws Exception {
        UpdateArticleRequest request = new UpdateArticleRequest("변경 제목", "변경 내용");
        String updateJsonContent = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(put("/articles/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJsonContent)
        );

        resultActions.andExpect(status().isBadRequest());
        assertThrows(IllegalArgumentException.class, () -> blogService.update(1L, request));
        assertThrows(IllegalArgumentException.class, () -> blogService.findById(1L));
    }
}