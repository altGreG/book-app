package com.bookApp.bookApp.dao.impl;

import com.bookApp.bookApp.Domain.User;
import com.bookApp.bookApp.dao.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {

        jdbcTemplate.update("INSER INTO appuser (username, email, password) VALUES (?, ?, ?)",
                            user.getUsername(),
                            user.getEmail(),
                            user.getPassword()
        );
    }
}
