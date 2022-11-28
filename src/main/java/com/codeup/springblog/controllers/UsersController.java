package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.users;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class UsersController {

    private final UserRepository userDao;
    public UsersController(UserRepository userDao){
        this.userDao = userDao;
    }

    @GetMapping
    public String allPosts(Model model){
        List<users> allUsers = userDao.findAll();
        model.addAttribute("allUsers", allUsers);
        return "/users";
    }


}
