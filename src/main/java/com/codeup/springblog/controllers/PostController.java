package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {
    @GetMapping("/posts")
    @ResponseBody
    public String postsController(){
        return "posts index page";
    }
    @GetMapping("/posts/{id}")
    @ResponseBody
    public String postsIdController(@PathVariable int id){
        return "view an individual post" + id;
    }
    @GetMapping("/posts/create")
    @ResponseBody
    public String createController(){
        return "view the form for creating a post";
    }
    @PostMapping("/posts/create")
    @ResponseBody
    public String createNewController(){
        return "create a new post";
    }
}
