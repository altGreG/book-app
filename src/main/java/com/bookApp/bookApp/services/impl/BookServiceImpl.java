package com.bookApp.bookApp.services.impl;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.User;
import com.bookApp.bookApp.dao.impl.BookDaoImpl;
import com.bookApp.bookApp.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookDaoImpl bookDB;

    public BookServiceImpl(BookDaoImpl bookDB) {
        this.bookDB = bookDB;
    }

    public Book createBook(String isbn, Book book) {
        try{
            bookDB.create(book);
            Book savedBook = bookDB.findOne(book.getISBN()).get();
            return savedBook;
        }catch(Exception er){
            return new Book();  // logging implemented in BookDaoImpl
        }
    }

}
