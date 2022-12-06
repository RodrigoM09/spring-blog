package com.codeup.springblog;

import com.codeup.springblog.models.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.codeup.springblog.models.Users;
import com.codeup.springblog.repositories.PostRepositories;
import com.codeup.springblog.repositories.UserRepository;

import javax.servlet.http.HttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBlogApplication.class)
@AutoConfigureMockMvc
public class BlogIntegrationTests {

    private Users testUser;

    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    PostRepositories postDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception{
        testUser = userDao.findByUsername("testUser");

        // Creates the test user if not exists
        if(testUser == null){
            Users newUser = new Users();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("pass"));
            newUser.setEmail("testUser@codeup.com");
            testUser = userDao.save(newUser);
        }

        // Throws a Post request to /login and expect a redirection to the Ads index page after being logged in
        httpSession = this.mvc.perform(post("/login").with(csrf())
                        .param("username", "testUser")
                        .param("password", "pass"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/posts"))
                .andReturn()
                .getRequest()
                .getSession();
    }
        @Test
        public void contextLoads(){
        //SANITY TEST, JUST TO MAKE SURE THE MVC BEAN IS WORKING---------------------------->
        assertNotNull(mvc);
        }

        @Test
        public void testIfUserSessionIsActive() throws Exception{
        // IT MAKES SURE THE RETURNED SESSION IS NOT NULL ----------->
        assertNotNull(httpSession);
        }


        // CREATE POST TEST------------------------------------------------------------>
        @Test
        public void testCreated() throws Exception{
        //MAKES A POST REQUEST TO /POSTS/CREATE AND EXPECT A REDIRECTION TO THE POST--------->
        this.mvc.perform(
                post("/posts/create").with(csrf())
                        .session((MockHttpSession) httpSession)
                //ADD ALL THE REQUIRED PARAMETERS TO YOUR REQUEST LIKE THIS------------>
                        .param("title", "test")
                        .param("body", "for sale"))
                .andExpect(status().is3xxRedirection());
        }

        @Test
        public void testShowPost() throws Exception{
        Post existingPost = postDao.findAll().get(0);

        //MAKES A GET REQUEST TO /POSTS/{ID} AND EXPECT A REDIRECTION TO THE POST SHOW PAGE
            this.mvc.perform(get("/posts/" + existingPost.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(existingPost.getBody())));
        }
        @Test
        public void testPostsIndex() throws Exception{
        Post existingPost = postDao.findAll().get(0);
            //MAKES A GET REQUEST TO /POSTS AND VERIFIES THAT WE GET SOME STATIC TEXT
            //OF THE STATIC TEXT OF THE POSTS/INDEX.HTML TEMPLATE AND AT LEAST THE TITLE
            // FROM THE POSTS IS PRESENT IN THE TEMPLATE
            this.mvc.perform(get("/posts"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Test")))
                    .andExpect(content().string(containsString(existingPost.getTitle())));
        }

        // EDIT POST TEST---------------------------------------------------------------->
    @Test
    public void testEditAd() throws Exception {
        // CREATES A TEST POST TO BE DELETED
        this.mvc.perform(
                        post("/posts/create").with(csrf())
                                .session((MockHttpSession) httpSession)
                                .param("title", "ad to be deleted")
                                .param("body", "won't last long"))
                .andExpect(status().is3xxRedirection());

        // GETS THE POST CREATED ABOVE FOR TEST PURPOSES
        Post existingPost = postDao.findByTitle("ad to be deleted");

        // Makes a Post request to /ads/{id}/edit and expect a redirection to the posts show page
        this.mvc.perform(
                        post("/posts/" + existingPost.getId() + "/edit").with(csrf())
                                .session((MockHttpSession) httpSession)
                                .param("title", "edited title")
                                .param("body", "edited description")
                                 .param("id", String.valueOf(existingPost.getId()))
                )

                .andExpect(status().is3xxRedirection());

        // Makes a GET request to /ads/{id} and expect a redirection to the Posts show page
        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                // Test the dynamic content of the page
                .andExpect(content().string(containsString("edited title")))
                .andExpect(content().string(containsString("edited description")));
    }

                    // DELETE TEST----------------------------------------------------->
    @Test
    public void testDeleteAd() throws Exception {
        // Creates a test POST to be deleted
        this.mvc.perform(
                        post("/posts/create").with(csrf())
                                .session((MockHttpSession) httpSession)
                                .param("title", "ad to deleted")
                                .param("body", "won't last long"))
                .andExpect(status().is3xxRedirection());

        // Get the recent POST that matches the title
        Post existingPost = postDao.findByTitle("ad to deleted");

        // Makes a Post request to /ads/{id}/delete and expect a redirection to the Ads index
        this.mvc.perform(
                        get("/posts/" + existingPost.getId() + "/delete").with(csrf())
                                .session((MockHttpSession) httpSession)
                                .param("id", String.valueOf(existingPost.getId())))
                .andExpect(status().is3xxRedirection());
    }
}
