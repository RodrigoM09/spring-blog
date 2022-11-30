package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.users;
import com.codeup.springblog.repositories.PostRepositories;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostRepositories postDao;
    private final UserRepository userDao;

    public PostController(PostRepositories postDao, UserRepository userDao){
        this.postDao = postDao;
        this.userDao = userDao;
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
    public String createController(Model model){
        model.addAttribute("post", new Post());
        return "/posts/create";
    }


    @PostMapping("/create")
    public String createNewController(@ModelAttribute Post post ){
        users user = userDao.findById(1L);
        post.setUser(user);
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

    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable long id, Model model){
        Post post = postDao.findById(id);
        model.addAttribute("post", post);
        return "/posts/edit";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@ModelAttribute Post post){
        users user = userDao.findById(1L);
        post.setUser(user);
        postDao.save(post);
        return "redirect:/posts";
    }
}
