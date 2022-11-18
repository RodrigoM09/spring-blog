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
        Post post1 = new Post(1, "First", "This is my first post");
        Post post2 = new Post(2, "Second", "This is my second post");
        Post post3 = new Post(3, "yo", "hey hey hey");
        List<Post> allPosts = new ArrayList<>(List.of(post1, post2, post3));
        Post post = null;
        for(Post userPost : allPosts){
            if(userPost.getId() == id){
                post = userPost;
            }
        }
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
}
