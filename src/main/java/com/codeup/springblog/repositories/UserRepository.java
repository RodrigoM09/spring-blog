package com.codeup.springblog.repositories;

import com.codeup.springblog.models.users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<users, Long> {
}
