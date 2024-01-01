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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
//@RequestMapping("/users")
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

    @GetMapping(path = "/users/library/{id}")
    public List<Book> listUsersLibrary(@PathVariable("id") Long userID){
        logger.info("Received 'Show Library' HTTP Get Request from user with id = " + userID);
        List<Book> userBooks = userService.findAllUsersBooks(userID);
        logger.info("Sent back HTTP Respond with all users books info");
        return userBooks;
    }

    @GetMapping(path = "/users/profile/{username}")
    public ResponseEntity<User> getUserInfo(@PathVariable("username") String username){
        logger.info("Received 'Show Profile' HTTP Get Request from user with username = " + username);
        User userInfo = userService.showUserInfo(username);
        logger.info("Sent back HTTP Respond with all users info");
        return new ResponseEntity<>(userInfo, HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/users/login/{username}/{password}")     // TODO: Change path, refactor function
    public String loginUserToApp(@PathVariable("username") String username,
                            @PathVariable("password") String password,
                            HttpServletResponse response){

        logger.info("Received request to login user with username: " + username);

        boolean goodCredentials = userService.checkCredentials(username, password);

        if(goodCredentials){
            User userData = userService.showUserInfo(username);

            Cookie usernameCookie = new Cookie("username", username);
            response.addCookie(usernameCookie);
            Cookie emailCookie = new Cookie("email", userData.getEmail());
            response.addCookie(emailCookie);
            Cookie idCookie = new Cookie("id", userData.getID().toString());
            response.addCookie(idCookie);

            logger.info("Sent back user cookies with:\n"+
                    "     -username: " + userData.getUsername() +
                    "\n     -email: "+ userData.getEmail() +
                    "\n     - id: " + userData.getID() +
                    "\nUser was granted with acces to app");
            return "subpage";
        }else{
            logger.info("User's access to app was denied");
            return "login";
        }
    }

//    Testowe API

    @GetMapping(path = "/users/library")
    public String showUsersLibrary(Model model){

        Book book = new Book();
        book.setTitle("Ostatnie życzenie ");
        book.setAuthor("Andrzej Sapkowski");
        book.setPublisher("SuperNowa");
        book.setReleaseDate("2014-09-25");
        book.setSeries("Wiedźmin Geralt z Rivii (tom 1)");
        book.setISBN("9788375780635");
        book.setCategory("Fantasy");
        book.setCoverUrl("https://s.lubimyczytac.pl/upload/books/240000/240310/1114358-352x500.jpg");

        List<Book> listOfBooks = List.of(book, book, book, book, book, book);

        model.addAttribute("books", listOfBooks);
        System.out.println(book);
        return "library";
    }
}
