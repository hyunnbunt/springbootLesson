package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.service.ArticleService;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;

    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm articleForm) {
        Article created = articleService.create(articleForm); // 저장한 객체 반환
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> patch(@PathVariable Long id,
                                         @RequestBody ArticleForm articleForm) {
        Article updated = articleService.patch(id, articleForm);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/api/articles/{id}/delete")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        List<CommentDto> commentDtoList = commentService.show(id);
        commentDtoList.forEach(commentDto -> {
            commentService.delete(commentDto.getId());
        });
        Boolean deleted = articleService.delete(id);
        return (deleted) ?
                ResponseEntity.status(HttpStatus.OK).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transactions-test")
    public ResponseEntity<List<Article>> createArticles(@RequestBody List<ArticleForm> articleForms) {
        List<Article> articles = articleService.createArticles(articleForms);
        return (articles != null) ?
                ResponseEntity.status(HttpStatus.OK).body(articles):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
