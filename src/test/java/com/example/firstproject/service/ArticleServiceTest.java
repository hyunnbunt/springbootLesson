package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        Article a = new Article(1l, "번트", "귀여워");
        Article b = new Article(2l, "하트", "귀여워");
        Article c = new Article(3l, "달래", "귀여워");
        Article d = new Article(4l, "제일 귀여운 강아지는?", "댓글 달아주세요");
        Article e = new Article(5l, "준석이 귀엽다 안 귀엽다", "귀엽다 1 안 귀엽다 2");
        Article f = new Article(6l, "번트는 너무 잘생김", "냉무");

        List<Article> expected = new ArrayList(Arrays.asList(a, b, c, d, e, f));

        List<Article> articles = articleService.index();

        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_ok() {
        Long id = 1l;
        Article expected = new Article(1l, "번트", "귀여워");

        Article article = articleService.show(id);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_fail() {
        Long id = -1l;
        Article article = articleService.show(id);

        assertEquals(null, article);
    }

    @Transactional
    @Test
    void create_ok() {
        // title, content 만 줬을 때
        String title = "준석";
        String content = "귀여워";
        ArticleForm articleForm = new ArticleForm(7l, title, content);
        Article expected = articleForm.toEntity();

        Article actual = articleService.create(new ArticleForm(null, title, content));

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void create_fail() {
        // id를 줬을 때 (db에서 자동 생성되어야만 함)
        Long id = 1l;
        String title = "준석";
        String content = "귀여워";
        ArticleForm articleForm = new ArticleForm(id, title, content);
        Article article = articleService.create(articleForm);

        assertEquals(null, article);
    }

    @Transactional
    @Test
    void patch_ok_1() {
        // 있는 id + title + content, 있는 id + title
        Long id = 2l;
        String title = "하토리";
        String content = "정말귀여워";
        ArticleForm articleForm = new ArticleForm(id, title, content);
        Article expected = articleForm.toEntity();

        Article article = articleService.patch(id, articleForm);
        assertEquals(expected.toString(), article.toString());
    }
    @Transactional
    @Test
    void patch_ok_2() {
        // 있는 id + title + content, 있는 id + title
        Long id = 2l;
        String title = "하토리";
        ArticleForm articleForm = new ArticleForm(id, title, "귀여워");
        Article expected = articleForm.toEntity();

        ArticleForm actualArticleForm = new ArticleForm(id, title, null);
        Article article = articleService.patch(id, actualArticleForm);

        assertEquals(expected.toString(), article.toString());
    }


    @Test
    void patch_fail_1() {
        // 없는 id
        Long id = -1l;
        String title = "하토리";
        String content = "정말귀여워";
        ArticleForm articleForm = new ArticleForm(id, title, content);
        Article article = articleService.patch(id, articleForm);

        assertEquals(null, article);
    }


    @Test
    void patch_fail_2() {
        // 있는 id + id만
        Long id = 1l;
        ArticleForm articleForm = new ArticleForm(id, null, null);
        Article article = articleService.patch(id, articleForm);

        assertEquals(null, article);

    }

    @Transactional
    @Test
    void delete_ok() {
        // 있는 id
        Long id = 3l;
        assertTrue(articleService.delete(id));
    }

    @Test
    void delete_fail() {
        // 없는 id
        Long id = -1l;
        assertFalse(articleService.delete(id));
    }
}