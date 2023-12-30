package com.bookApp.bookApp.controllers;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.User;
import com.bookApp.bookApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
public class UserController {

    protected static final Logger logger = LogManager.getLogger();

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

//    REST API service

    @PostMapping(path = "/api/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        logger.info("Received 'Add User' HTTP POST request");
        User savedUser =  userService.createUser(user);
        logger.info("Sent back HTTP Respond with saved user info to client");
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/api/users/library/{id}")
    public List<Book> listUsersLibrary(@PathVariable("id") Long userID){
        logger.info("Received 'Show Library' HTTP Get Request from user with id = " + userID);
        List<Book> userBooks = userService.findAllUsersBooks(userID);
        logger.info("Sent back HTTP Respond with all users books info");
        return userBooks;
    }

    @GetMapping(path = "/api/users/profile/{username}")
    public ResponseEntity<User> getUserInfo(@PathVariable("username") String username){
        logger.info("Received 'Show Profile' HTTP Get Request from user with username = " + username);
        User userInfo = userService.showUserInfo(username);
        logger.info("Sent back HTTP Respond with all users info");
        return new ResponseEntity<>(userInfo, HttpStatus.NOT_FOUND);
    }


//    WebService

    @GetMapping(path = "/api/users/login/{username}/{password}")     // TODO: Change path, refactor function
    public String loginUserToApp(@PathVariable("username") String username,
                            @PathVariable("password") String password,
                            HttpServletResponse response){

        boolean goodCredentials = userService.checkCredentials(username, password);

        if(goodCredentials){

            User userData = userService.showUserInfo(username);

            Cookie usernameCookie = new Cookie("username", username);
            response.addCookie(usernameCookie);
            Cookie emailCookie = new Cookie("email", userData.getEmail());
            response.addCookie(emailCookie);
            Cookie idCookie = new Cookie("id", userData.getID().toString());
            response.addCookie(idCookie);

            return "subpage";
        }else{
            return "login";
        }
    }
}
