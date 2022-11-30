package com.codeup.springblog.repositories;

import com.codeup.springblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepositories extends JpaRepository<Post, Long>{
    Post deleteByTitle(String title);
    Post findById(long id);
    Post deleteById(long id);
}