package com.bookApp.bookApp.dao.impl;

import com.bookApp.bookApp.Domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        Book book = new Book();
        book.setTitle("Kuba w Dżungli!");
		book.setAuthor("Kuba Donosik");
		book.setPublisher("Wydawnictwo Mag");

//        underTest.create(book);
        Optional<Book> result = underTest.findOne("Kuba");
        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(book);
    }

}
