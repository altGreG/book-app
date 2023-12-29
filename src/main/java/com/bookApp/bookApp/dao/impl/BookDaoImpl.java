package com.bookApp.bookApp.dao.impl;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.dao.BookDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao {
    protected static final Logger logger = LogManager.getLogger();
    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        try{
            jdbcTemplate.update("INSERT INTO book (title, author, publisher, isbn) VALUES (?, ?, ?, ?)",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getISBN());

            logger.info("Successfully added book with isbn = " +
                    book.getISBN() + " and title = " + book.getTitle() +
                    " to database");
        }catch (Exception er){
            logger.warn("Failed to add book with isbn = " +
                    book.getISBN() + " and title = " + book.getTitle() +
                    " to database");
            logger.error(er);
        }
    }

    @Override
    public Optional<Book> findOne(String isbn) {
        try{
            List<Book> results = jdbcTemplate.query("SELECT * FROM book WHERE isbn = '" + isbn + "' LIMIT 1",
                    new BookRowMapper());
            if(results.stream().findFirst().isEmpty()){
                logger.info("There is no book in database with isbn = " + isbn);
                return Optional.empty();
            }else {
                logger.info("Successfully retrieved book with isbn = " + isbn + " from database");
                return results.stream().findFirst();
            }
        }catch (Exception er){
            logger.warn("Failed to retrieve book with isbn = " + isbn + " from database");
            logger.error(er);
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAny(String bookTitle) {

        try{
            List<Book> results = jdbcTemplate.query("SELECT * FROM book WHERE title like '%" + bookTitle + "%'",
                    new BookRowMapper());

            if(results.isEmpty()){
                logger.info("There is no book in database with title similar to " + bookTitle);
                return null;
            }else{
                logger.info("Successfully retrieved books with title similar to " + bookTitle + " from database");
                for (Book booksFromDb : results){
                    System.out.println(booksFromDb + "\n");
                }
                return results;
            }
        }catch(Exception er) {
            logger.warn("Failed to retrieved books with title similar to " + bookTitle);
            logger.error(er);
            return null;
        }
    }

    public static class BookRowMapper implements RowMapper<Book>{
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

            Book booktmp = new Book();
            booktmp.setBookID(rs.getLong("book_id"));
            booktmp.setTitle(rs.getString("title"));
            booktmp.setAuthor(rs.getString("author"));
            booktmp.setPublisher(rs.getString("publisher"));
            booktmp.setSeries(rs.getString("series"));
            booktmp.setReleaseDate(rs.getString("release_date"));
            booktmp.setISBN(rs.getString("isbn"));
            booktmp.setCategory(rs.getString("category"));
            booktmp.setCoverUrl(rs.getString("cover_url"));

            return booktmp;
        }
    }

    @Override
    public void update(String isbn, Book book) {
        try {
            jdbcTemplate.update("UPDATE book SET " +
                    "title = '" + book.getTitle() + "', " +
                    "author = '" + book.getAuthor() + "', " +
                    "publisher = '" + book.getPublisher() + "', " +
                    "series = '" + book.getSeries() + "', " +
                    "release_date= '" + book.getReleaseDate() + "', " +
                    "category = '" + book.getCategory() + "', " +
                    "cover_url = '" + book.getCoverUrl() + "' WHERE isbn = " + isbn);
            logger.info("Book info with ISBN: " + book.getISBN() + " has been updated in database");
        }catch(Exception er){
            logger.warn(er);
        }

    }
}
