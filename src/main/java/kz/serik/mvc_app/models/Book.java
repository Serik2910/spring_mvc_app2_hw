package kz.serik.mvc_app.models;

import jakarta.validation.constraints.*;

public class Book {
    private int id;
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 1, max = 100, message = "Name length should be more than 0 and less than 101 characters")
    private String name;
    @NotEmpty(message = "Author shouldn't be empty")
    @Size(min = 1, max = 100, message = "Author full name should be should be more than 0 and less than 101 characters")
    private String author;
    @Min(value = 0, message = "year should be greater than 0")
    @Max(value = 2024, message = "future year is too fantastic")
    private int year;
    private Integer personId;

    public Book() {
    }

    public Book(int id, String name, String author, int year, Integer personId) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.personId = personId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}
