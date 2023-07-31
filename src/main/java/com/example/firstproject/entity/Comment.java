package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter // 쌤은 안 추가
@Slf4j
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 질문, 지난 에러 관련
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id") // 대상 정보의 칼럼 이름을 article_id로 설정
    private Article article; // id를 넣으면 jpa가 정확히 인식하지 못할 수 있음.
    @Column
    private String nickname;
    @Column
    private String body;

    public static Comment fromDto(CommentDto commentDto, Article article) {
        if (commentDto.getArticleId() != article.getId()) {
            throw new IllegalArgumentException("게시글의 id가 잘못되었습니다.");
        }
        if (commentDto.getId() != null) {
            throw new IllegalArgumentException("댓글의 id가 없어야 합니다.");
        }
        return new Comment(commentDto.getId(), article, commentDto.getNickname(), commentDto.getBody());
    }

    public CommentDto toDto() {
        return new CommentDto(this.id, this.article.getId(), this.nickname, this.body);
    }

    public void patch(CommentDto commentDto) {
        if (this.id != commentDto.getId()) {
            throw new IllegalArgumentException("댓글의 id가 잘못되었습니다.");
        }

        if (commentDto.getNickname() != null) {
            this.nickname = commentDto.getNickname();
        }
        if (commentDto.getBody() != null) {
            this.body = commentDto.getBody();
        }
    }
}
