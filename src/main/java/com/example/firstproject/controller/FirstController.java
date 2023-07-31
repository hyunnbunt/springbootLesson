package com.example.firstproject.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hi")
    public String niceToMeetYou(Model model) {
        model.addAttribute("username", "번트");
        return "greetings"; //templates/greetings.mustache -> 브라우저로 전송
    }

    @GetMapping("/bye")
    public String goodBye(Model model) {
        model.addAttribute("username", "번트");
        return "farewells"; // templates/farewells.mustache -> 브라우저로 전송
    }


}
