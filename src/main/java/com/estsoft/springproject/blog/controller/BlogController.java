package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.DTO.AddArticleRequest;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.DTO.ArticleResponse;
import com.estsoft.springproject.blog.domain.DTO.UpdateArticleRequest;
import com.estsoft.springproject.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 로그객체
@Tag(name = "블로그 저장/수정/삭제/조회용 API", description = "API 설명을 이곳에 작성")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BlogController {
    private final BlogService blogService;
    
    // RequestMapping (특정 url    POST /articles)
//    @RequestMapping(method = RequestMethod.POST)
    // 위 아래 같음
    @PostMapping("/articles")
    @Operation(summary = "블로그 글 쓰기")
    public ResponseEntity<ArticleResponse> writeArticle(@RequestBody AddArticleRequest request) {
        // logging level
        // trace, debug, info, warn, error
        log.debug("{}, {}", request.getTitle(), request.getContent());

        Article article = blogService.saveArticle(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ArticleResponse(article));
    }

    @Operation(summary = "블로그 전체 목록 보기", description = "블로그 메인 화면에서 보여주는 전체 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "100", description = "요청에 성공했습니다.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> findAll() {
        List<ArticleResponse> articleList = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleList);
    }

    @Operation(summary = "블로그 조회하기")
    @Parameter(name = "id", description = "블로그 글 ID", example = "45")
    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> findById(@PathVariable Long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok(new ArticleResponse(article));
    }

    @Operation(summary = "삭제할 블로그 ID")
    @Parameter(name = "id", description = "블로그 글 ID", example = "1")
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        blogService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // /articles/{id} 수정 API request body
    @Operation(summary = "블로그 글 수정하기", description = "수정 내용")
    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> updateById(@PathVariable Long id,
                                                      @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);
        return ResponseEntity.ok(new ArticleResponse(updatedArticle));
    }
}