package com.bookApp.bookApp.services;

import com.bookApp.bookApp.Domain.Book;

public interface ScraperService {

    Book getFromLubimyCzytac(String bookUrl);

}
