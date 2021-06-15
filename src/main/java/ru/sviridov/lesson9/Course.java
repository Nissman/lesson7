package ru.sviridov.lesson9;

public class Course implements ICourse {
    private String title;

    public Course(String title) {
        this.title = title;
    }

    @Override
    public String getCourseTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Title='" + title + '\'' +
                '}';
    }
}
