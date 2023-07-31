package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // ?
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    void findByArticleId() {
        {
            // 4번 게시글의 모든 댓글을 조회
            // 입력 데이터의 준비
            Long articleId = 4l;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 결과 예상하기
            Article article = new Article(articleId, "제일 귀여운 강아지는?", "댓글 달아주세요");
            Comment a = new Comment(1l, article, "은아", "번트요");
            Comment b = new Comment(2l, article, "bunt_mom", "하트");
            List<Comment> expected = Arrays.asList(a, b);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 리");
        }
        {
            // 댓글이 없는 게시글의 모든 댓글을 조회
            // 입력 데이터의 준비
            Long articleId = 7l;
            Article article = new Article(articleId, "오늘 날씨가 참 좋네요...", "산책 가실 분?");

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 결과 예상하기
            List<Comment> expected = new ArrayList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "댓글이 없는 게시글의 빈 댓글 리스트를 리턴");
        }
        {
            // 9번 게시글의 모든 댓글을 조회
            // 입력 데이터의 준비
            Long articleId = 9l;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 결과 예상하기
            List<Comment> expected = new ArrayList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "댓글이 없는 게시글의 빈 댓글 리스트를 리턴");
        }
        {
            // 9999번 게시글의 모든 댓글을 조회
            // 입력 데이터의 준비
            Long articleId = 9999l;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 결과 예상하기
            List<Comment> expected = new ArrayList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "댓글이 없는 게시글의 빈 댓글 리스트를 리턴");
        }
        {
            // 9999번 게시글의 모든 댓글을 조회
            // 입력 데이터의 준비
            Long articleId = -1l;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 결과 예상하기
            List<Comment> expected = new ArrayList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "없는 게시글 댓글 리스트를 리턴");
        }
    }

    @Test
    void findByNickname() {
        {
            // 입력 데이터의 준비
            String nickname = "bunt_mom";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 결과 예상하기
            Article article1 = new Article(4l, "제일 귀여운 강아지는?", "댓글 달아주세요");
            Comment a = new Comment(2l, article1, "bunt_mom", "하트");
            Article article2 = new Article(5l, "준석이 귀엽다 안 귀엽다", "귀엽다 1 안 귀엽다 2");
            Comment b = new Comment(4l, article2, "bunt_mom", "111");
            List<Comment> expected = Arrays.asList(a, b);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "bunt_mom의 모든 댓글을 출력");
        }
        {
            // 입력 데이터의 준비
            String nickname = "은아";
            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 결과 예상하기
            Article article1 = new Article(4l, "제일 귀여운 강아지는?", "댓글 달아주세요");
            Comment a = new Comment(1l, article1, "은아", "번트요");
            Article article2 = new Article(6l, "번트는 너무 잘생김", "냉무");
            Comment b = new Comment(6l, article2, "은아", "맞아맞아");
            List<Comment> expected = Arrays.asList(a, b);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "은아의 모든 댓글을 출력");
        }
        {
            // 입력 데이터의 준비
            String nickname = null;
            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 결과 예상하기
            List<Comment> expected = new ArrayList<>();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "닉네임에 null이 들어간 조회 결과");
        }
        {
            // 입력 데이터의 준비
            String nickname = "";
            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 결과 예상하기
            List<Comment> expected = new ArrayList<>();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "닉네임에 빈 스트링이 들어간 조회 결과");
        }
    }
}