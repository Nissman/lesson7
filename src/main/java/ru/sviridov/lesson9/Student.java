package ru.sviridov.lesson9;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Student implements IStudent {

    private String name;
    private List<Course> CourseList;

    public Student(String name, Course[] courses) {
        this.name = name;
        this.CourseList = Arrays.asList(courses);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Course> getAllCourses() {
        return CourseList;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
