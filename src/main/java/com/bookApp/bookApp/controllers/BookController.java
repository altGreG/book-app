package com.bookApp.bookApp.controllers;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.User;
import com.bookApp.bookApp.services.BookService;
import com.bookApp.bookApp.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    protected static final Logger logger = LogManager.getLogger();

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(path = "/api/books/{isbn}")
    public ResponseEntity<Book> createBook(@PathVariable("isbn") String isbn,
                                           @RequestBody Book book){
        logger.info("Received add book HTTP POST request");
        Book savedBook =  bookService.createBook(isbn, book);
        logger.info("Sent back HTTP Respond with saved book info to client");
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }


}
