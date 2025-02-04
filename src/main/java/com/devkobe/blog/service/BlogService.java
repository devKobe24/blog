package com.devkobe.blog.service;

import com.devkobe.blog.domain.Article;
import com.devkobe.blog.dto.AddArticleRequest;
import com.devkobe.blog.dto.UpdateArticleRequest;
import com.devkobe.blog.repository.BlogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    // DB에 저장되어 있는 글을 모두 가져오는 메서드
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // 글 하나를 조회하는 메서드
    public Article findById(Long id) {
        return blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Not found id =======>>>>>>> " + id));
    }

    // 글 삭제 메서드
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }


    @Transactional // 트랜잭션 메서드
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Not Found id =======>>>>>>> " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
