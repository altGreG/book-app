package com.bookApp.bookApp.services;

import com.bookApp.bookApp.Domain.Book;

import java.io.IOException;

public interface ScraperService {

    Book getFromLubimyCzytac(String bookUrl) throws IOException;

}
