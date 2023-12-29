package com.bookApp.bookApp.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @JsonProperty("id")
    private Long bookID;

    private String title;

    private String author;

    private String publisher;

    private String releaseDate;

    private String series;

    private String isbn;

    private String category;

    private String coverUrl;

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long newID) {
        this.bookID = newID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String newAuthor) {
        this.author = newAuthor;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String newPublisher) {
        this.publisher = newPublisher;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String newReleaseDate) {
        this.releaseDate = newReleaseDate;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String newSeries) {
        this.series = newSeries;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String newISBN) {
        this.isbn = newISBN;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String newCategory) {
        this.category = newCategory;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String newCoverUrl) {
        this.coverUrl = newCoverUrl;
    }

}
