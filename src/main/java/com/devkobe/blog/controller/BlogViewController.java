package com.devkobe.blog.controller;

import com.devkobe.blog.domain.Article;
import com.devkobe.blog.dto.ArticleListViewResponse;
import com.devkobe.blog.dto.ArticleViewResponse;
import com.devkobe.blog.service.BlogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;
    // 블로그 글 전체 리스트를 담은 뷰를 반환하는 메서드, GET
    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
            .map(ArticleListViewResponse::new)
            .toList();
        model.addAttribute("articles", articles); // 1. 블로그 글 리스트 저장.

        return "articleList"; // 2. articleList.html라는 뷰 조회
    }

    // 블로그 글을 반환할 메서드, GET
    // 메서드의 인자(argument)인 id에 URL로 넘어온 값을 받아 findById() 메서드로 넘겨
    // 글을 조회하고, 화면에서 사용할 모델에 데이터를 저장한 다음, 보여줄 템플릿 이름을 반환합니다.
    @GetMapping("articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    // 수정 화면을 보여주기 위한 메서드.
    @GetMapping("/new-article")
    // 1. id 키를 가진 쿼리 파라미터 값을 id 변수에 매핑(id는 없을 수도 있음)
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) { // 2. id가 없으면 생성.
            model.addAttribute("article", new ArticleViewResponse());
        }

        return "newArticle";
    }
}
