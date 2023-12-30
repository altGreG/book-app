package com.bookApp.bookApp.controllers;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.User;
import com.bookApp.bookApp.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    protected static final Logger logger = LogManager.getLogger();

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

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

}
