package ru.sviridov.lesson7.jackson_demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Author {

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "second_name")
    private String secondName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}
