package com.bookApp.bookApp.controllers;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.services.impl.ScraperServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/scraper")
public class ScraperController {

    protected static final Logger logger = LogManager.getLogger();

    private ScraperServiceImpl accessScraper;

    public ScraperController(ScraperServiceImpl accessScraper) {
        this.accessScraper = accessScraper;
    }

    @GetMapping(path="/getBook")
    public String getBook(@RequestParam(name="bookUrl", defaultValue="not_provided") String bookUrl, Model model){

        logger.info("Get request to scrap data from: " + "\n    - "+ bookUrl);

        Book scrapedBook = accessScraper.getFromLubimyCzytac(bookUrl);
        if(scrapedBook == null){
            Book book = new Book();
            book.setTitle("Ostatnie życzenie ");
            book.setAuthor("Andrzej Sapkowski");
            book.setPublisher("SuperNowa");
            book.setReleaseDate("2014-09-25");
            book.setSeries("Wiedźmin Geralt z Rivii (tom 1)");
            book.setISBN("9788375780635");
            book.setCategory("Fantasy");
            book.setCoverUrl("https://s.lubimyczytac.pl/upload/books/240000/240310/1114358-352x500.jpg");
            model.addAttribute("book", book);
        }else{
            model.addAttribute("book", scrapedBook);
        }


        logger.info("Returned scraped data of the book");

        return "add-book-scraped";
    }
}
