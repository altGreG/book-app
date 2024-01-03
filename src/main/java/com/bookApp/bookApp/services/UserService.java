package com.bookApp.bookApp.services;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<Book> findAllUsersBooks(Long userID);

    User showUserInfo(String username);

    void addToUserLibrary(Long userID, Long bookID) throws Exception;


}
