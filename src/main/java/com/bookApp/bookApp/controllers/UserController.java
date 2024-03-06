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
        logger.info("Sent back HTTP Respond with saved user info to client \n\n");
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/users/profile/")
    public String getUserInfo(@CookieValue(value = "username", defaultValue = "0") String username,
                              Model model){
        logger.info(">>> LOADING USERS PROFILE PAGE MODULE <<<");

        logger.info("Received 'Show Profile' request from user with username: '" + username + "' ");
        User userInfo = userService.showUserInfo(username);
        model.addAttribute("user", userInfo);
        logger.info("Sent to user: All User Info \n\n");
        return "login";     // TODO: Change template to valid one
    }

    @GetMapping(path = "/users/library")
    public String showUsersLibrary(@CookieValue(value = "id", defaultValue = "0") String userID,
                                   @CookieValue(value = "username", defaultValue = "empty") String username,
                                   @CookieValue(value = "addToLibraryError", defaultValue = "no_error") String addToLibraryError,
                                   @CookieValue(value = "Error", defaultValue = "no_error") String Error,
                                   Model model, HttpServletResponse response){

        logger.info(">>> LOADING USERS LIBRARY PAGE MODULE <<<");

        if(!Objects.equals(addToLibraryError, "no_error")){
            model.addAttribute("error_message", addToLibraryError.replace("_", " "));

            if(Objects.equals(addToLibraryError, "Successfully_Added_Book_To_Library")){
                model.addAttribute("error_css", "display: flex; color: var(--color6);");
            }else{
                model.addAttribute("error_css", "display: flex;");
            }

            Cookie addLibraryErrorCookie = new Cookie("addToLibraryError", "no_error");
            addLibraryErrorCookie.setPath("/");
            response.addCookie(addLibraryErrorCookie);

        }

        if(!Objects.equals(Error, "no_error")){
            model.addAttribute("error_message", Error.replace("_", " "));

            model.addAttribute("error_css", "display: flex;");

            Cookie ErrorCookie = new Cookie("Error", "no_error");
            ErrorCookie.setPath("/");
            response.addCookie(ErrorCookie);

        }

        if(!Objects.equals(userID, "0") && !Objects.equals(username, "empty")){
            Long userID2 = Long.parseLong(userID);
            logger.info("User is logged in. Cookies exist!");
            logger.info("Received 'Show Library' request from user with id: " + userID2);
            List<Book> userBooks = userService.findAllUsersBooks(userID2);
            model.addAttribute("books", userBooks);
            model.addAttribute("username", username);
            logger.info("Sent to user:  Data With Info About All User Books");
            logger.info("Sent user to: Library Page \n\n");
            return "library";
        }else{
            logger.warn("User isn't logged in. Cookies didn't exist!");
            logger.info("Sent user to: Login Page \n\n");
            return "login";
        }
    }

    @GetMapping(path = "/users/addToLibrary")
    public ResponseEntity<Object> addToUsersLibrary(@CookieValue(value = "id", defaultValue = "0") String userID,
                                                    @CookieValue(value = "bookid", defaultValue = "0") String bookID,
                                                    Model model, HttpServletResponse response) throws IOException, URISyntaxException {

        logger.info(">>> ADDING BOOK TO USERS LIBRARY MODULE <<<");

        logger.info("Get request to add book with id: " + bookID + " to library of user with id: " + userID);
        try {
            if(Objects.equals(bookID, "0")){
                throw new Exception("Cannot read cookies with book id!");
            }
            userService.addToUserLibrary(Long.parseLong(userID), Long.parseLong(bookID));

            logger.info("Sent back user cookies with:\n" +
                    "     - addToLibraryError: " + "Successfully_Added_Book_To_Library");

            Cookie addLibraryErrorCookie = new Cookie("addToLibraryError", "Successfully_Added_Book_To_Library");
            addLibraryErrorCookie.setPath("/");
            response.addCookie(addLibraryErrorCookie);

            URI library = new URI("http://localhost:8080/users/library");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(library);

            logger.info("Redirect user to: Library Page \n\n");
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

//            return "library";   // TODO: Do redirection to /users/library
        }catch (Exception er){
            logger.error(er);
            if(Objects.equals(userID, "0")){
                logger.warn("User need to login to app again! No cookies with user id!");

                URI login = new URI("http://localhost:8080/");
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setLocation(login);
                logger.info("Redirect user to: Login Page \n\n");
                return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

//                return "login";
            }else{

                String error_message = er.getMessage();

                if(Objects.equals(error_message, "Book with id: " + bookID + " is already in user library!")){
                    logger.info("Sent back user cookies with:\n" +
                            "     - addToLibraryError: " + "Error:_Already_in_library");

                    Cookie addLibraryErrorCookie = new Cookie("addToLibraryError", "Error:_Already_in_library");
                    addLibraryErrorCookie.setPath("/");
                    response.addCookie(addLibraryErrorCookie);
                }else {

                    logger.info("Sent back user cookies with:\n" +
                            "     - addToLibraryError: " + "Error:_Unable_to_add_to_library");

                    Cookie addLibraryErrorCookie = new Cookie("addToLibraryError", "Error:_Unable_to_add_to_library");
                    addLibraryErrorCookie.setPath("/");
                    response.addCookie(addLibraryErrorCookie);
                }

                URI library = new URI("http://localhost:8080/users/library");
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setLocation(library);
                logger.warn("Book wasn't add to library. Redirect user to: Library Page With Error Message \n\n");
                return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

//                return "library";   // TODO: Do redirection to /users/library, give back info to user
            }
        }
    }
}
