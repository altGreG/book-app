package com.bookApp.bookApp.services.impl;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.User;
import com.bookApp.bookApp.dao.impl.UserDaoImpl;
import com.bookApp.bookApp.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private UserDaoImpl userDB;

    public UserServiceImpl(UserDaoImpl userDaoImpl) {
        this.userDB = userDaoImpl;
    }

    @Override
    public User createUser(User user) {
        try{
            userDB.create(user);
            User savedUser = userDB.findOne(user.getUsername()).get();
            return savedUser;
        }catch(Exception er){
            return new User();  // logging implemented in UserDaoImpl
        }
    }

    @Override
    public List<Book> findAllUsersBooks(Long userID) {
        try{
            return StreamSupport.stream(userDB
                            .findUserBooks(userID)
                            .spliterator(),
                            false)
                    .collect(Collectors.toList());
        }catch(Exception er)
        {
            return null;    // logging implemented in UserDaoImpl
        }
    }

    @Override
    public User showUserInfo(String username) {
        try{
            User retrievedUser = userDB.findOne(username).get();
            retrievedUser.setPassword("#####Tu#Nie#Patrzymy####");
            return retrievedUser;
        }catch (Exception er){
            return new User();  // logging implemented in UserDaoImpl
        }
    }

//    WebService


    @Override
    public boolean checkCredentials(String username, String password) {
        return userDB.checkCredentials(username, password);
    }
}
