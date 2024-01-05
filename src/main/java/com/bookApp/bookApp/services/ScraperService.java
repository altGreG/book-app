package com.bookApp.bookApp.services;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.Domain.ScraperObjects.LubimyCzytacListItem;

import java.io.IOException;
import java.util.List;

public interface ScraperService {

    Book getFromLubimyCzytac(String bookUrl) throws IOException;

    List<LubimyCzytacListItem> getListOfBooksFromLubimyCzytac(String phrase);

}
