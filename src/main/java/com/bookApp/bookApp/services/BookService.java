package com.bookApp.bookApp.services;

import com.bookApp.bookApp.Domain.Book;

import java.util.Optional;

public interface BookService {

    Book createBook(String isbn, Book book) throws Exception;

    Book findOneBookService(String isbn);

}
