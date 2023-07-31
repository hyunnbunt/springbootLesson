package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결!
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());

        // 1. Dto를 변환! Entity:
        Article article = form.toEntity();
        log.info(article.toString());
        // 2. Repository에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        // 1: id로 데이터를 가져옴!
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.show(id);
        // 2: 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);
        // 3: 보여줄 페이지를 설정!

        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        List<Article> articleList = articleRepository.findAll();
        log.info(articleList.toString());
        model.addAttribute("articleList", articleList);
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article article = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", article);
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm articleForm) {
        Article article = articleForm.toEntity();
        Article target = articleRepository.findById(article.getId()).orElse(null);
        if (target != null) {
            articleRepository.save(article);
        }
        return "redirect:/articles/" + article.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        // 댓글 리스트 불러오기
        List<CommentDto> commentDtos = commentService.show(id);

        // 댓글 전체 삭제
        commentDtos.forEach(commentDto -> {
            commentService.delete(commentDto.getId());
        });

        // 1. 삭제 대상을 가져온다
        Article target = articleRepository.findById(id).orElse(null);
        // 2. 대상을 삭제한다
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제 완료");
        }
        // 3. 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }
}
