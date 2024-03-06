package com.bookApp.bookApp.services.impl;

import com.bookApp.bookApp.dao.impl.UserDaoImpl;
import com.bookApp.bookApp.services.LoginAndSignUpService;
import org.springframework.stereotype.Service;

@Service
public class LoginAndSignUpServiceImpl implements LoginAndSignUpService {

    private UserDaoImpl userDB;

    public LoginAndSignUpServiceImpl(UserDaoImpl userDB) {
        this.userDB = userDB;
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        return userDB.checkCredentials(username, password);
    }

}
