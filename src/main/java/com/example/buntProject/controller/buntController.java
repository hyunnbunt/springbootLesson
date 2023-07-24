package com.example.buntProject.controller;

import com.example.buntProject.dto.BuntFriend;
import com.example.buntProject.entity.BuntFriendEntity;
import com.example.buntProject.repository.BuntRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class buntController {
    @Autowired
    BuntRepository buntRepository;
    @GetMapping("/dogfriends")
    public String buntFriendsList(Model model) {
        List<BuntFriendEntity> buntFriendsEntityList = buntRepository.findAll();
        model.addAttribute("buntFriendsEntityList", buntFriendsEntityList);
        return "register/dogFriends";
    }

    @GetMapping("/dogfriends/new")
    public String newBuntFriend() {
        return "register/new";
    }

    @PostMapping("/dogfriends/create")
    public String createNewBuntFriend(BuntFriend buntFriend) {
        BuntFriendEntity buntFriendEntity = buntFriend.toEntity();
        buntFriendEntity = buntRepository.save(buntFriendEntity);
        return "redirect:/dogfriend/" + buntFriendEntity.getId();
    }

    @GetMapping("dogfriend/{id}")
    public String newBuntDetail(@PathVariable Long id, Model model) {
        BuntFriendEntity buntFriendEntity = buntRepository.findById(id).orElse(null);
        model.addAttribute("newDogFriend", buntFriendEntity);
        return "register/newDogFriend";
    }
 }
