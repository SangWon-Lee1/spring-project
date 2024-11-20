package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.DTO.AddArticleRequest;
import com.estsoft.springproject.blog.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BlogControllerTest2 {
    @InjectMocks
    BlogController controller;

    @Mock
    BlogService service;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @DisplayName("게시글 정보 저장 API 테스트")
    @Test
    public void testSaveArticle() throws Exception {
        String title = "mock_title";
        String content = "mock_content";
        // given
        // {
        //  "title" : "mock_title",
        //  "content" : "mock_content"
        //  }
        // 직렬화 : 객체 -> Json(String)
        AddArticleRequest request = new AddArticleRequest(title, content);
        ObjectMapper objectMapper = new ObjectMapper();
        String articleJson = objectMapper.writeValueAsString(request);

        // stub (service.saveArticle호출시 위에서 만든 article을 리턴하도록 stub처리)
        Mockito.when(service.saveArticle(any()))
                .thenReturn(new Article(title, content));

        // when (API 호출)
        ResultActions resultActions = mockMvc.perform(post("/api/articles")
                .content(articleJson)
                .contentType(MediaType.APPLICATION_JSON));

        // then (호출 결과 검증)
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(title))
                .andExpect(jsonPath("content").value(content));
    }

    @DisplayName("게시글 삭제")
    @Test
    public void testDeleteArticle() throws Exception {
        // given
        Long articleId = 1L;

        Mockito.doNothing().when(service).deleteById(articleId);

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/articles/{id}", articleId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        verify(service, times(1)).deleteById(articleId);
    }

    @DisplayName("게시글 단건 조회")
    @Test
    public void testFindOne() throws Exception {
        // given
        Long id = 1L;

        // stubing : article 객체 만듬
        Mockito.doReturn(new Article("title", "content"))
                .when(service).findById(id);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/articles/{id}", id));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("title").value("title"))
                .andExpect(jsonPath("content").value("content"));
    }
}