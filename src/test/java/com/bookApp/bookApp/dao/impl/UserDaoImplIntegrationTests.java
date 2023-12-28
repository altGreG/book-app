package com.bookApp.bookApp.dao.impl;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserDaoImplIntegrationTests {


    private  UserDaoImpl underTest;

    @Autowired
    public UserDaoImplIntegrationTests(UserDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatWeCanGetUsersBooks() {

        User user = new User();
        Optional<User> result = underTest.findOne("example");
        System.out.println(result);
        user = result.get();
        System.out.println(user);
        user.setPassword("NoweSuperHaslo1@#$");
        user.setEmail("newexample@xample.net");

        underTest.update(user.getID(), user);


//        List<Book> result2 = underTest.findUserBooks(1L);
//        System.out.println(result2);
////        System.out.println(result2.get(2).getTitle());
    }

}
