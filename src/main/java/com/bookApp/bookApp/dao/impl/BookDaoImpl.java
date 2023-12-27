package com.bookApp.bookApp.dao.impl;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.dao.BookDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, publisher) VALUES (?, ?, ?)",
                            book.getTitle(),
                            book.getAuthor(),
                            book.getPublisher()
        );
    }
}
