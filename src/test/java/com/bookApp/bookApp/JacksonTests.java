package com.bookApp.bookApp;

import com.bookApp.bookApp.Domain.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class JacksonTests{

    @Test
    public void testThatObjectMapperCanCreateJsonFromObject() throws JsonProcessingException {

//        ObjectMapper objectMapper = new ObjectMapper();
//        Book book = new Book();
//        book.setTitle("Wiedźmin Tom.1");
//        book.setAuthor("Andrzej Sapkowski");
//        book.setIsbn("9788375780635");
//        book.setPublisher("SuperNowa");
//        book.setCategory("Fantastyka");
//
//        // object to JSON
//        String result = objectMapper.writeValueAsString(book);
//        assertThat(result).isEqualTo("{\"bookID\":null,\"title\":\"Wiedźmin Tom.1\",\"author\":\"Andrzej Sapkowski\",\"publisher\":\"SuperNowa\",\"releaseDate\":null,\"series\":null,\"isbn\":\"9788375780635\",\"category\":\"Fantastyka\",\"coverUrl\":null}");
    }

    @Test
    public void testThatObjectMapperCanCreateObjectFromJson() throws JsonProcessingException {
//        String json = "{\"bookID\":null,\"title\":\"Wiedźmin Tom.1\",\"author\":\"Andrzej Sapkowski\",\"publisher\":\"SuperNowa\",\"releaseDate\":null,\"series\":null,\"isbn\":\"9788375780635\",\"category\":\"Fantastyka\",\"coverUrl\":null}";
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Book result = objectMapper.readValue(json, Book.class);
//
//        Book book = new Book();
//        book.setTitle("Wiedźmin Tom.1");
//        book.setAuthor("Andrzej Sapkowski");
//        book.setIsbn("9788375780635");
//        book.setPublisher("SuperNowa");
//        book.setCategory("Fantastyka");
//
//        assertThat(result).isEqualTo(book);
    }

}
