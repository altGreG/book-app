package com.bookApp.bookApp.dao;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void create(User user);

    Optional<User> findOne(String username);

    List<Book> findUserBooks(Long userID);

    void update(Long userID, User User);

    void addBookToLibrary(Long userID, Long bookID) throws Exception;

    void removeBookFromLibrary(Long userID, Long bookID);

    boolean checkCredentials(String username, String password);
}
