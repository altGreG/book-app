package com.bookApp.bookApp.services.impl;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.services.ScraperService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.thymeleaf.util.StringUtils;

@Service
public class ScraperServiceImpl implements ScraperService {

    protected static final Logger logger = LogManager.getLogger();

    @Override
    public Book getFromLubimyCzytac(String bookUrl){
        try{

            if(!bookUrl.contains("lubimyczytac.pl")){
                throw new Exception("Wrong url address, unable to scrape data");
            }

            Book scrapedBook = new Book();
            Document site = Jsoup.connect(bookUrl).get();

            // title
            Elements value = site.select(".book__title");
            scrapedBook.setTitle(value.text());

            //author
            value = site.select("span.author a");
            scrapedBook.setAuthor(value.text());

            //publisher
            value = site.select("span.book__txt a");
            scrapedBook.setPublisher(value.text());

            //category
            value = site.select("a.book__category");
            scrapedBook.setCategory(value.text());

            //cover url
            value = site.select("img.img-fluid");
            scrapedBook.setCoverUrl(value.attr("src"));

            //book details
            Elements values = site.select("div#book-details *");

            String bookDetails = values.text();
            String[] bookDetailsArray = bookDetails.split(" ");

            ArrayList<String> splittedBookDetails = new ArrayList<String>();
            String oneBookDetail = "";

            // loop extract data from div with book details
            for(String oneElement:bookDetailsArray){
                if(oneElement.contains(":")){
                    splittedBookDetails.add(oneBookDetail);
                    oneBookDetail = oneElement + " ";
                    if(splittedBookDetails.size() >= 11){
                        break;
                    }
                }else{
                    oneBookDetail += oneElement + " ";
                }
            }

            //save data to book object
            for(String detail:splittedBookDetails){

                //series
                if(detail.contains("Cykl:")){
                    detail = detail.substring(6);
                    scrapedBook.setSeries(detail);
                }

                //isbn
                if(detail.contains("ISBN:")){
                    detail = detail.substring(6);
                    scrapedBook.setISBN(detail);
                }

                //release date
                if(detail.contains("wydania:") && scrapedBook.getReleaseDate() == null){
                    detail = detail.substring(9);
                    detail = detail.substring(0,10);
                    scrapedBook.setReleaseDate(detail);
                }
            }

            logger.info("Successfully scraped data:\n"+
                    "     - " + scrapedBook.getTitle() +
                    "\n     - " + scrapedBook.getAuthor() +
                    "\n     - " + scrapedBook.getPublisher() +
                    "\n     - " + scrapedBook.getReleaseDate() +
                    "\n     - " + scrapedBook.getISBN() +
                    "\n     - " + scrapedBook.getSeries() +
                    "\n     - " + scrapedBook.getCategory() +
                    "\n     - " + scrapedBook.getCoverUrl());

            return scrapedBook;

        }catch (Exception er){
            logger.warn("Unable to extract data from given url");
            logger.info(er);

            return  null;
        }
    }
}
