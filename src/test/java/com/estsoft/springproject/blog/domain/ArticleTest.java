package com.estsoft.springproject.blog.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    @Test
    public void test() {
        Article article = new Article("제목", "내용");
        // 위랑 아래(빌더사용)같음 빌더사용하면 가독성이 좋아짐
        Article articleBuilder = Article.builder()
                .title("제목")
                .content("내용")
                .build();
    }
}