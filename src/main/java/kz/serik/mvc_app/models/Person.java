package kz.serik.mvc_app.models;

import jakarta.validation.constraints.*;

/**
 * @author Admin on 23.09.2023
 * @project spring_mvc_app2
 */
public class Person {
    private int id;
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 3, max = 100, message = "Name length should be more than 3 and less than 100 characters")
    private String fullname;
    @Min(value = 1900, message = "Birth year should be greater than 1900")
    @Max(value = 2023, message = "future birth is too fantastic")
    private int birthyear;
    public Person() {}
    public Person(int id, String fullname, int birthyear) {
        this.id = id;
        this.fullname = fullname;
        this.birthyear = birthyear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(int birthyear) {
        this.birthyear = birthyear;
    }
}
