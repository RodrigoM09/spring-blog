package com.codeup.springblog.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> posts;

    public users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public users() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
