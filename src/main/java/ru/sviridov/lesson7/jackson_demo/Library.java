package ru.sviridov.lesson7.jackson_demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

public class Library {

    private String title;

    private Book[] books;

    @JsonIgnore
    private boolean isWorking;

    public Library(String title, Book[] books, boolean isWorking) {
        this.title = title;
        this.books = books;
        this.isWorking = isWorking;
    }

    public Library() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Library{" +
                "title='" + title + '\'' +
                ", books=" + Arrays.toString(books) +
                '}';
    }
}


