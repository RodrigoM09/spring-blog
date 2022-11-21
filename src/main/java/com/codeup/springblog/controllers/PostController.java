package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.repositories.PostRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostRepositories postDao;

    public PostController(PostRepositories postDao){
        this.postDao = postDao;
    }

    @GetMapping
    public String allPosts(Model model){
        List<Post> allPosts = postDao.findAll();
        model.addAttribute("allPosts", allPosts);
        return "/posts/index";
    }

    @GetMapping("/show")
    public String postsController(){
        return "/posts/show";
    }

    @GetMapping("/{id}")
    public String onePost(@PathVariable long id, Model model){
        Post post = postDao.findById(id);
        model.addAttribute("post", post);
        return "/posts/show";
    }


    @GetMapping("/create")
    public String createController(){
        return "/posts/create";
    }


    @PostMapping("/create")
    public String createNewController(@RequestParam(name="title") String title, @RequestParam(name="body") String body ){
        Post post = new Post(title, body);
        postDao.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/delete")
    public String deleteController(){
        return "/posts/delete";
    }

    @PostMapping("/delete")
    public String deleteByTitle(@RequestParam(name="title") String title){
        Post post = postDao.deleteByTitle(title);
        postDao.delete(post);
        return "redirect:/posts";
    }
}
