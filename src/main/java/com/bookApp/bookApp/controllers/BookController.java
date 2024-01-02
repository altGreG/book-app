package com.bookApp.bookApp.controllers;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.services.BookService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class BookController {

    protected static final Logger logger = LogManager.getLogger();

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/books/addBook")
    public String addBookTemplate(@CookieValue(value = "id", defaultValue = "0") String userID,
                                  @CookieValue(value = "username", defaultValue = "Unknown") String username,
                                  Model model){
        if(Objects.equals(userID, "0")){
            logger.warn("User need to login to app again! No cookies with user id!");
            return "login";
        }else{
            model.addAttribute("username", username);
            return "add-book";
        }
    }

    @PostMapping(path = "/books/addToDatabase")
    public String addBookToDatabase(@CookieValue(value = "id", defaultValue = "0") String userID,
                                    @CookieValue(value = "username", defaultValue = "Unknown") String username,
                                    Book book, Model model, HttpServletResponse response){

        if(Objects.equals(userID, "0")){
            logger.warn("User need to login to app again! No cookies with user id!");
            return "login";
        }

        model.addAttribute("username", username);

        logger.info("Get request to add book with isbn:" + book.getIsbn() + " and title: " + book.getTitle() + " to database");

        try{
            Book savedBook = bookService.createBook(book.getIsbn(), book);
            model.addAttribute("savedBook", savedBook);

            Cookie bookIDCookie = new Cookie("bookid", savedBook.getBookID().toString());
            bookIDCookie.setPath("/");
            response.addCookie(bookIDCookie);

            return "add-book-to-library";
        }catch(Exception er){
            logger.warn(er);

            String er_message = er.getMessage();

            model.addAttribute("error_message", "Error: " + er_message);
            model.addAttribute("error_css", "max-width: 400px;color: var(--color7); font-size: 18px; padding: 10px; border: 2px solid var(--color1);");

            logger.info("Return to user template with error message");

            return "add-book";
        }
    }
}
