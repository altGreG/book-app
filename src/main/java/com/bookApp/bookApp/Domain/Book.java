package com.bookApp.bookApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    private Long bookID;

    private String title;

    private String author;

    private String publisher;

    private String releaseDate;

    private String series = "Unknown";

    public String isbn;

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String newISBN) {
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
