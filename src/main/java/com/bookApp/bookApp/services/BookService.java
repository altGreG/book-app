package com.bookApp.bookApp.services;

import com.bookApp.bookApp.Domain.Book;

public interface BookService {

    Book createBook(String isbn, Book book) throws Exception;

}
