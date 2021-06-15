package ru.sviridov.lesson9;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static List<Student> studentList = new ArrayList<>();
    static Course course_1;
    static Course course_2;
    static Course course_3;
    static Course course_4;
    static Course course_5;
    static Course course_6;
    static Course course_7;
    static Student student_1;
    static Student student_2;
    static Student student_3;
    static Student student_4;
    static Student student_5;

    public static void main(String[] args) {
        //создане курсов
        createCourses();
        //создание студентов
        createStudents();
        //запуск созданных функций
        startFunc();
    }

    //печать результата вызванных функций
    private static void startFunc() {
        System.out.print("Уникальные курсы, на которые подписаны студенты: ");
        for (Course course : getUniq(studentList)) {
            System.out.printf("%s ", course.getCourseTitle());
        }
        System.out.print("\nСамые любознательные студенты: ");
        for (Student student : getTheCurious(studentList)) {
            System.out.printf("%s ", student.getName());
        }
        System.out.printf("\nСписок студентов посещяющих курс %s: ", course_5.getCourseTitle());
        for (Student student : getStudentsOnTheCourse(studentList, course_5)) {
            System.out.printf("%s ", student.getName());
        }
    }

    private static void createStudents() {
        student_1 = new Student("Вася", new Course[]{course_5, course_2});
        student_2 = new Student("Петя", new Course[]{course_1, course_2, course_3, course_4});
        student_3 = new Student("Дима", new Course[]{course_1, course_2, course_3, course_4, course_5, course_6});
        student_4 = new Student("Саша", new Course[]{course_1, course_2, course_3, course_4, course_5});
        student_5 = new Student("Ваня", new Course[]{course_1, course_2, course_3, course_4});
        //добавление студентов в общий список
        addToList(student_1, student_2, student_3, student_4, student_5);
    }

    private static void createCourses() {
        course_1 = new Course("1");
        course_2 = new Course("2");
        course_3 = new Course("3");
        course_4 = new Course("4");
        course_5 = new Course("5");
        course_6 = new Course("6");
        course_7 = new Course("7");
    }

    private static void addToList(Student... student) {
        Collections.addAll(studentList, student);
    }

    // функция, принимающая список Student и возвращающая список уникальных курсов, на которые подписаны студенты.
    public static List<Course> getUniq(List<Student> studentList) {
        return studentList.stream()
                .flatMap(s -> s.getAllCourses().stream())
                .distinct()
                .sorted(Comparator.comparingInt(c -> c.getCourseTitle().charAt(0)))
                .collect(Collectors.toList());
    }

    //функция, принимающая на вход список Student и возвращающая список из трех самых любознательных (любознательность определяется количеством курсов).
    public static List<Student> getTheCurious(List<Student> studentList) {
        return studentList.stream()
                .sorted((s1, s2) -> s2.getAllCourses().size() - s1.getAllCourses().size())
                .limit(3)
                .collect(Collectors.toList());
    }

    //функцая, принимающая на вход список Student и экземпляр Course, возвращающая список студентов, которые посещают этот курс.
    public static List<Student> getStudentsOnTheCourse(List<Student> studentList, Course course) {
        return studentList.stream()
                .filter(student -> student.getAllCourses().contains(course))
                .collect(Collectors.toList());

    }
}
