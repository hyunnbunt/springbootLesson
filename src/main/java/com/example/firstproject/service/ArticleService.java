package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(@RequestBody ArticleForm articleForm) {
        Article newArticle = articleForm.toEntity();
        if (newArticle.getId() != null) {
            return null;
        }
        return articleRepository.save(newArticle);
    }

    public Article patch(@PathVariable Long id,
                                         @RequestBody ArticleForm articleForm) {
        // 수정할 엔티티 생성
        Article updateArticle = articleForm.toEntity();
        // 대상 엔티티를 조회
        Article target = articleRepository.findById(id).orElse(null);
        // 잘못된 요청 처리 (데이터가 없거나 id가 다른 경우)
        if (target == null || id != updateArticle.getId()) {
            return null;
        }
        if (!target.patch(updateArticle)) {
            return null;
        }
        // 업데이트
        return articleRepository.save(target);
    }

    public Boolean delete(@PathVariable Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null) {
            return false;
        }
        articleRepository.delete(target);
        return true;
    }

    @Transactional // 해당 메소드를 트랜잭션으로 묶는다!
    public List<Article> createArticles(List<ArticleForm> articleForms) {
        List<Article> articleList = articleForms.stream()
                .map(articleForm -> articleForm.toEntity())
                .collect(Collectors.toList());
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        articleRepository.findById(-1l).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!"));
        return articleList;
    }
}
