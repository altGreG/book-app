package com.bookApp.bookApp.dao.impl;

import com.bookApp.bookApp.Domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookDaoImplIntegrationTests {

    private BookDaoImpl underTest;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
//        Book book = new Book();
//        book.setTitle("Kuba w Dżungli!");
//		book.setAuthor("Kuba Donosik");
//		book.setPublisher("Wydawnictwo Mag");
//        book.setIsbn("12344534573894");
//
////        underTest.create(book);
//
//        Optional<Book> result = underTest.findOne("12344534573894");
//        assertThat(result).isPresent();
//        Book bookFromDB = result.get();
//        bookFromDB.setBookID(null);
//        assertThat(bookFromDB).isEqualTo(book);
    }

    @Test
    public void testThatCanWeFindAnyBookWithTitleSimilarToProvided() {
//        List<Book> result = underTest.findAny("Kuba");
////        System.out.println(result);
    }

}
