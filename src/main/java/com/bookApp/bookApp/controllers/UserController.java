package com.bookApp.bookApp.controllers;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.User;
import com.bookApp.bookApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    protected static final Logger logger = LogManager.getLogger();

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        logger.info("Received 'Add User' HTTP POST request");
        User savedUser =  userService.createUser(user);
        logger.info("Sent back HTTP Respond with saved user info to client");
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/users/profile/")
    public String getUserInfo(@CookieValue(value = "username", defaultValue = "0") String username,
                              Model model){
        logger.info("Received 'Show Profile' HTTP Get Request from user with username = " + username);
        User userInfo = userService.showUserInfo(username);
        model.addAttribute("user", userInfo);
        logger.info("Sent back HTTP Respond with all users info");
        return "login";     // TODO: Change template to valid one
    }

    @GetMapping(path = "/users/library")
    public String showUsersLibrary(@CookieValue(value = "id", defaultValue = "0") String userID,
                                   @CookieValue(value = "username", defaultValue = "empty") String username,
                                   Model model){

        if(!Objects.equals(userID, "0") && !Objects.equals(username, "empty")){
            Long userID2 = Long.parseLong(userID);
            System.out.println("Cookies");
            logger.info("Received 'Show Library' HTTP Get Request from user with id = " + userID2);
            List<Book> userBooks = userService.findAllUsersBooks(userID2);
            model.addAttribute("books", userBooks);
            model.addAttribute("username", username);
            logger.info("Sent back HTTP Respond with all users books info");
            return "library";
        }else{
            System.out.println("No cookies");
            return "login";
        }
    }

    @GetMapping(path = "/users/addToLibrary")
    public ResponseEntity<Object> addToUsersLibrary(@CookieValue(value = "id", defaultValue = "0") String userID,
                                          @CookieValue(value = "bookid", defaultValue = "0") String bookID) throws IOException, URISyntaxException {
        logger.info("Get request to add book with id: " + bookID + " to library of user with id: " + userID);
        try {
            if(Objects.equals(bookID, "0")){
                throw new Exception("Cannot read cookies with book id!");
            }
            userService.addToUserLibrary(Long.parseLong(userID), Long.parseLong(bookID));

            URI library = new URI("http://localhost:8080/users/library");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(library);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

//            return "library";   // TODO: Do redirection to /users/library
        }catch (Exception er){
            logger.warn(er);
            if(Objects.equals(userID, "0")){
                logger.warn("User need to login to app again! No cookies with user id!");

                URI login = new URI("http://localhost:8080/");
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setLocation(login);
                return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

//                return "login";
            }else{

                URI library = new URI("http://localhost:8080/users/library");
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setLocation(library);
                return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

//                return "library";   // TODO: Do redirection to /users/library, give back info to user
            }
        }
    }
}
