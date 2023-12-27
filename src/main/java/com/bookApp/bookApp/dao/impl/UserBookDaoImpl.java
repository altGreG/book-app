package com.bookApp.bookApp.dao.impl;

import com.bookApp.bookApp.dao.UserBookDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserBookDaoImpl implements UserBookDao {

    private final JdbcTemplate jdbcTemplate;

    public UserBookDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
