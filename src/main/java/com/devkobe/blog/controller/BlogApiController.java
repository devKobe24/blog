package com.devkobe.blog.controller;

import com.devkobe.blog.domain.Article;
import com.devkobe.blog.dto.AddArticleRequest;
import com.devkobe.blog.dto.AriticleResponse;
import com.devkobe.blog.service.BlogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    // HTTP 메서드가 POST일 때 전달 받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);
        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedArticle);
    }

    // 전체 글을 조회한 뒤 반환하는 메서드
    @GetMapping("/api/articles")
    public ResponseEntity<List<AriticleResponse>> findAllArticles() {
        List<AriticleResponse> articles = blogService.findAll()
            .stream()
            .map(AriticleResponse::new)
            .toList();

        return ResponseEntity.ok()
            .body(articles);
    }

    // 글 하나를 반환하는 메서드
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<AriticleResponse> findArticle(@PathVariable Long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
            .body(new AriticleResponse(article));
    }
}
