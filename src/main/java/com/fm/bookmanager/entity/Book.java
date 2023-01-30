package com.fmexperiments.application.entity;

import java.time.LocalDate;

import javax.persistence.Entity;

@Entity
public class Book extends AbstractEntity {

    private String name;
    private String author;
    private LocalDate publicationDate;
    private Integer pages;
    private String eanCode;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public LocalDate getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
    public Integer getPages() {
        return pages;
    }
    public void setPages(Integer pages) {
        this.pages = pages;
    }
    public String getEanCode() {
        return eanCode;
    }
    public void setEanCode(String isbn) {
        this.eanCode = isbn;
    }

}
