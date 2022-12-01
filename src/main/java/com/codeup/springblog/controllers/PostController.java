package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.Users;
import com.codeup.springblog.repositories.PostRepositories;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/myPosts")
    public String myPosts(Model model){
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> myPosts = postDao.findAllByUser(user);
        model.addAttribute("myPosts", myPosts);
        return "/posts/myPosts";
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
    public String createNewController(@ModelAttribute Post post){
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        user = userDao.findById(userId);
        post.setUser(user);
        postDao.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/delete")
    public String deleteByTitle(@PathVariable long id){
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postDao.findById(id);
        // this is id from users table
        long usersId = user.getId();
        // this is users_id from posts table
        long postUserId = post.getUser().getId();
        if (usersId == postUserId ) {
            postDao.delete(post);
        }
            return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable long id, Model model){
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postDao.findById(id);
        model.addAttribute("post", post);
        // this is id from users table
        long usersId = user.getId();
        // this is users_id from posts table
        long postUserId = post.getUser().getId();
        if (usersId == postUserId ) {
            return "/posts/edit";
        }else {
            return "redirect:/posts";
        }
    }

    @PostMapping("/{id}/edit")
    public String editPost(@ModelAttribute Post post, @RequestParam(name="id") long id){
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        postDao.save(post);
        return "redirect:/posts";
    }
}
