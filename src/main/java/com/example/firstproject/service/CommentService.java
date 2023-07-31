package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleRepository articleRepository;

    // 해당 게시글의 모든 댓글을 조회
    public List<CommentDto> show(Long articleId) {
        // 게시글이 존재하는지 확인. 없으면 예외 발생
        articleRepository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException()
        );

        // db에서 댓글 엔티티 목록 꺼내기
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 댓글 엔티티를 dto로 변환
        List<CommentDto> commentDtos =
                comments.stream()
                .map(comment -> comment.toDto())
                .collect(Collectors.toList());
        return commentDtos;
    }
    @Transactional
    public CommentDto upload(Long articleId, CommentDto commentDto) {
        // 게시글이 존재하는지 확인. 없으면 예외 발생
        // sql에서는 article_id 컬럼에만 정보를 채워서 보내면 db가 article에 연결한다
        // jpa는 article을 컬럼으로 하지만 실제로 db에서 join되는 컬럼은
        // article_id임을 적어둔다(@JoinColumn(name="article_id"))
        // java에서 jpa의 entity를 만들 때는 db가 아닌 jpa가 이해하는 방식으로 소통하므로
        // article_id를 이용해 article을 db에서 찾아서 컬럼을 채워준다?
        // article_id를 주면 jpa는 이걸 db처럼 article로 연결할 수 없음?
//        log.info("입력값 => {}", articleId);
//        log.info("입력값 => {}", commentDto.toString());

        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException()
        );

        // 새로운 엔티티를 만들어서 db에 넣기
        Comment comment = Comment.fromDto(commentDto, article);
        CommentDto createdDto = commentRepository.save(comment).toDto();
//        log.info("반환값 => {}", createdDto);
        return createdDto;
    }

    @Transactional
    public CommentDto update(Long id, CommentDto commentDto) {
        Comment target = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다")
        );
        // entity를 patch할 때 참조하는 파라미터가 꼭 entity여야 하나? 아님 선생님은 그렇게 안 함.
        target.patch(commentDto);
        commentRepository.save(target);
        return target.toDto();
    }

    @Transactional
    public CommentDto delete(Long id) {
        Comment target = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다")
        );
        commentRepository.delete(target);
        return target.toDto();
    }
}
