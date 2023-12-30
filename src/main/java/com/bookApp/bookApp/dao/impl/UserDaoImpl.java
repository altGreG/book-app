package com.bookApp.bookApp.dao.impl;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.User;
import com.bookApp.bookApp.dao.UserDao;
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
public class UserDaoImpl implements UserDao {

    protected static final Logger logger = LogManager.getLogger();
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {

        try{
            jdbcTemplate.update("INSERT INTO appuser (username, email, password) VALUES (?, ?, ?)",
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword());
            logger.info("Added user with:" +
                    "username = " + user.getUsername() +
                    ", email = " + user.getEmail() +
                    ", password = " + "WE PROTECT USER'S SUPER SAFE PASSWORD'S ;)");
        }catch(Exception er){
            logger.warn("Failed to add user into database!");
            logger.error(er);
        }
    }

    @Override
    public Optional<User> findOne(String username) {
        List<User> results = jdbcTemplate.query("SELECT * FROM appuser WHERE username = ? LIMIT 1",
                new UserDaoImpl.UserRowMapper(), username);
        return results.stream().findFirst();
    }

    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {

            User usertmp = new User();
            usertmp.setID(rs.getLong("id"));
            usertmp.setUsername(rs.getString("username"));
            usertmp.setEmail(rs.getString("email"));
            usertmp.setPassword(rs.getString("password"));

            return usertmp;
        }
    }

    @Override
    public List<Book> findUserBooks(Long userID) {
        try{
            List<Book> results = jdbcTemplate.query("Select b.book_id,  b.title, b.author, b.publisher," +
                            " b.series, b.release_date, b.isbn, b.category, b.cover_url  from appUser as ap" +
                            " INNER JOIN userBook as ub ON ap.id = ub.user_id" +
                            " INNER JOIN book as b ON ub.book_id = b.book_id" +
                            " WHERE ap.id = " + userID,
                    new BookDaoImpl.BookRowMapper());
            if(!results.isEmpty()){
                logger.info("Successfully retrieved users books");
                return results;
            }else {
                logger.info("Users don't have any books in his library " +
                        "or there is no user with id = " + userID);
                return null;
            }
        }
        catch (Exception er){
            logger.warn("Failed to access users library");
            logger.error(er);
            return null;
        }
    }



    @Override
    public void update(Long userID, User user) {
        try {
            jdbcTemplate.update("UPDATE appuser SET " +
                    "username = '" + user.getUsername() + "', " +
                    "email = '" + user.getEmail() + "', " +
                    "password = '" + user.getPassword() + "' WHERE id = " + userID);
            logger.info("User info with id: " + userID + " has been updated in database");
        }catch(Exception er){
            logger.warn("Failed to update user data");
            logger.error(er);
        }
    }

    @Override
    public void addBookToLibrary(Long userID, Long bookID) {
        try{
            jdbcTemplate.update("INSERT INTO userbook (user_id, book_id) VALUES (?, ?)",
                    userID, bookID);
            logger.info("Book with id = " + bookID + " has been added to user library");
        }catch (Exception er){
            logger.warn("Failed to add book into user's library");
            logger.error(er);
        }
    }

    @Override
    public void removeBookFromLibrary(Long userID, Long bookID) {
        try{
            jdbcTemplate.update("DELETE FROM userbook WHERE user_id = ? and book_id = ?",
                    userID, bookID);
            logger.info("Book with id = " + bookID + " has been deleted from user library");
        }catch (Exception er){
            logger.warn("Failed to remove book from user's library");
            logger.error(er);
        }
    }

    @Override
    public boolean checkCredentials(String username, String password) {     // TODO: Do beeter check and logging

        List<User> result = jdbcTemplate.query("Select * from appuser WHERE" +
                " username = '"+username+
                "' and password = '" + password + "'"
                , new UserRowMapper());

        if(result.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}
