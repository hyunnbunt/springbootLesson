package com.example.firstproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CommentDto {
    Long id;
    // json에서 사용하는 이름의 convention과
    // java에서 사용하는 이름의 convention을 다르게 할 때 사용.
    // db의 컬럼명과는 전혀 연관 없음
    @JsonProperty("article_id")
    Long articleId;
    String nickname;
    String body;

}
