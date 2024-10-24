package com.estsoft.springproject.blog.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsContent {
    private Long postId;
    private String body;
}
