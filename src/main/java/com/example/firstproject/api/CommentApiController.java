package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    CommentService commentService;

    // 해당 게시글의 모든 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> show(@PathVariable Long articleId) {
        // 서비스에 작업 요청 후 dto 리스트 반환받기
        List<CommentDto> commentDtos = commentService.show(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(commentDtos);
    }

    // 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> upload(@PathVariable Long articleId,
                                             @RequestBody CommentDto commentDto) {

        // 서비스에 작업 요청 후 db에서 생성된 id가 포함된 새로운 dto 반환받기
        CommentDto created = commentService.upload(articleId, commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    // 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id,
                                             @RequestBody CommentDto commentDto) {

        // 서비스에 작업 요청 후 새로운 정보가 저장된 dto 반환받기
        CommentDto updated = commentService.update(id, commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
        CommentDto deleted = commentService.delete(id);
        // 서비스에 작업 요청 후 삭제한 dto를 그대로 반환
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

}
