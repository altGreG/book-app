package com.bookApp.bookApp.services.impl;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.dao.impl.BookDaoImpl;
import com.bookApp.bookApp.services.BookService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookDaoImpl bookDB;

    public BookServiceImpl(BookDaoImpl bookDB) {
        this.bookDB = bookDB;
    }

    public Book createBook(String isbn, Book book) throws Exception {
        bookDB.create(book);
        Optional<Book> savedBooks = bookDB.findOne(book.getIsbn());

        if(!savedBooks.isEmpty()){
            Book savedBook = savedBooks.get();
            return savedBook;
        }else{
            return new Book();  // logging implemented in BookDaoImpl
        }

    }

}
