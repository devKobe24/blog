package com.devkobe.blog.dto;

import com.devkobe.blog.domain.Article;
import lombok.Getter;

@Getter
public class AriticleResponse {

    private final String title;
    private final String content;

    // title, content 필드 엔티티를 인수로 받는 생성자
    public AriticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
