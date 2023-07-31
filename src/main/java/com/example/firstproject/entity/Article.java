package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity // DB가 해당 객체를 인식 가능!
@Slf4j
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    public boolean patch(Article updateArticle) {
        if (updateArticle.title == null && updateArticle.content == null) {
            return false;
        }
        if (updateArticle.title != null) {
            this.title = updateArticle.title;
        }
        if (updateArticle.content != null) {
            this.content = updateArticle.content;
        }
        return true;
    }
}
