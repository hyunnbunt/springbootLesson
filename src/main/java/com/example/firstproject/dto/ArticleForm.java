package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class ArticleForm {
    private Long id;
    private String title;
    private String content;

    public static Article toArticle(ArticleForm articleForm) {
        return new Article(articleForm.id, articleForm.title, articleForm.content);
    }

    public Article toEntity() {
         return new Article(id, title, content);
    }
}
