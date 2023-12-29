package com.bookApp.bookApp.dao;

import com.bookApp.bookApp.Domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    void create(Book book);

    Optional<Book> findOne(String bookTitle);

    List<Book> findAny(String isbn);

    void update(String isbn, Book book);

}
