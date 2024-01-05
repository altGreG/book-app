package com.bookApp.bookApp.controllers;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.ScraperObjects.LubimyCzytacListItem;
import com.bookApp.bookApp.services.impl.ScraperServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ScraperServiceIntegrationTest {

    private ScraperServiceImpl underTest;

    @Autowired
    public ScraperServiceIntegrationTest(ScraperServiceImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void checkWhetherFunctionGetValidDataFromUrl(){
        System.out.println("Start of test");

        Book result = underTest.getFromLubimyCzytac("https://lubimyczytac.pl/ksiazka/240310/ostatnie-zyczenie");

    }

    @Test
    public void checkWhetherFunctionReturnListOfItems(){
        System.out.println("Start of test");

        List<LubimyCzytacListItem> result = underTest.getListOfBooksFromLubimyCzytac("ostatnie");

    }
}
