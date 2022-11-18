package com.codeup.springblog.controllers;

import models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    @GetMapping("/show")
    public String postsController(){
        List<Post> posts = new ArrayList<>();
        return "/posts/show";
    }

    @GetMapping("/show/{id}")
    public String postsIdController(@PathVariable int id, Model model){
        Post post = new Post();
        model.addAttribute("post", model);
        return "/posts/show";
    }


    @GetMapping("/show/create")
    @ResponseBody
    public String createController(){
        return "view the form for creating a post";
    }


    @PostMapping("/show/create")
    @ResponseBody
    public String createNewController(){
        return "create a new post";
    }
}
