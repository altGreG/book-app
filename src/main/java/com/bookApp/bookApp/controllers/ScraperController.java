package com.bookApp.bookApp.controllers;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.ScraperObjects.LubimyCzytacListItem;
import com.bookApp.bookApp.services.impl.ScraperServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    public String getBook(@RequestParam(name="bookUrl", defaultValue="not_provided") String bookUrl,
                          @CookieValue(value = "username", defaultValue = "Unknown") String username,
                          Model model){

        logger.info(">>> SCRAPING DATA FROM LUBIMYCZYRAC.PL MODULE <<<");

        model.addAttribute("username", username);

        logger.info("Get request to scrap data from: " + "\n    - "+ bookUrl);

        Book scrapedBook = accessScraper.getFromLubimyCzytac(bookUrl);
        if(scrapedBook == null){
            Book book = new Book();

            book.setReleaseDate("00/00/0000");

            model.addAttribute("book", book);
            logger.info("Data can't be sraped");
            logger.info("Sent user to: Can't Srape Data Page \n\n");
            return "add-book-not-scraped";
        }else{
            model.addAttribute("book", scrapedBook);
            logger.info("Sent to user: Scraped Data About Book \n\n");
            return "add-book-scraped";
        }

    }

    @GetMapping(path="/getListOfBooks")
    public ResponseEntity<Object> getListOfBooks(@RequestParam(name="phrase", defaultValue="not_provided") String phrase,
                                                 Model model, HttpServletResponse response) throws URISyntaxException {
        // TODO: Refactor code
        logger.info(">>> SCRAPING DATA FROM LUBIMYCZYRAC.PL MODULE  (List of Books) <<<");
        logger.info("Get request to retrieve list of books with name similar to: " + "\n    - "+ phrase);

        List<LubimyCzytacListItem> ListOfItems = accessScraper.getListOfBooksFromLubimyCzytac(phrase);

        if(ListOfItems.isEmpty()){

            logger.warn("List of books, can't be sraped");

            Cookie ErrorCookie = new Cookie("Error", "Error:_No_Books_With_Name_Similar_To_Provided");
            ErrorCookie.setPath("/");
            response.addCookie(ErrorCookie);
            logger.info("Sent to user Cookie with error message: \n" +
                    "   -" + "Error: No Books With Name Similar To Provided");

            logger.info("Redirect user to: Library Page \n\n");
            URI library = new URI("http://localhost:8080/users/library");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(library);

            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }else{
            logger.info("Successfully retrieved list of books \n\n");
            URI library = new URI("http://localhost:8080/scraper/search-book-page/" + phrase.replace(" ", "+"));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(library);

            return new ResponseEntity<>(httpHeaders , HttpStatus.SEE_OTHER);
        }
    }

    @GetMapping(path="/search-book-page/{phrase}")
    public String getSearchListPage(@CookieValue(value = "username", defaultValue = "Unknown") String username,
                                    @PathVariable("phrase") String phrase,
                                    Model model){
        model.addAttribute("username", username);

        logger.info(">>> RETURN SEARCH BOOK PAGE MODULE <<<");

        List<LubimyCzytacListItem> ListOfItems = accessScraper.getListOfBooksFromLubimyCzytac(phrase.replace("_", "+"));
        model.addAttribute("listItems", ListOfItems);

        logger.info("Sent to user: Scraped List Of Books");
        logger.info("Sent user to: Scraped List Of Books Page \n\n");
        return "search-book";
    }
}
