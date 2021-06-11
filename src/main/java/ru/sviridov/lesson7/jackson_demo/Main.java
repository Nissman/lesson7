package ru.sviridov.lesson7.jackson_demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class Main {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"title\":\"Ленинская библиотека\",\"books\":[{\"author\":\"Пушкин\",\"title\":\"Станционный смотритель\",\"pages\":35,\"year\":1820, \"count\": {\"a\": 123 }},{\"author\":\"Лукьяненко\",\"title\":\"Ночной дозор\",\"pages\":350,\"year\":2004},{\"author\":\"Дейл Карнеги\",\"title\":\"Как перестать нервничать\",\"pages\":200,\"year\":1960}]}";
        int pushkin = objectMapper
                .readTree(json)
                .at("/books/0/count/a")
                .asInt();

        System.out.println(pushkin);
    }

    private static void customizeFieldNames() throws JsonProcessingException {
        Author a = new Author();
        a.setFirstName("Володя");
        a.setSecondName("Одуванчиков");

        System.out.println(objectMapper.writeValueAsString(a));

        String jsonAuthor = "{\"first_name\":\"Володя\",\"second_name\":\"Одуванчиков\"}";
        System.out.println(objectMapper.readValue(jsonAuthor, Author.class));
    }

    private static void deserializationFeatureFailOnUnknown() throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String networkResponse =
                "{\"title\":\"Ленинская библиотека\",\"address\": \"ул.Ленина 14\",\"books\":[{\"author\":\"Пушкин\",\"title\":\"Станционный смотритель\",\"pages\":35,\"year\":1820},{\"author\":\"Лукьяненко\",\"title\":\"Ночной дозор\",\"pages\":350,\"year\":2004},{\"author\":\"Дейл Карнеги\",\"title\":\"Как перестать нервничать\",\"pages\":200,\"year\":1960}]}";
        Library libraryFromString = objectMapper.readValue(networkResponse, Library.class);
        System.out.println(libraryFromString);
    }

    private static void readingIntoCollection() throws JsonProcessingException {
        String jsonBooksList = "[{\"author\":\"Пушкин\",\"title\":\"Станционный смотритель\",\"pages\":35,\"year\":1820},{\"author\":\"Лукьяненко\",\"title\":\"Ночной дозор\",\"pages\":350,\"year\":2004},{\"author\":\"Дейл Карнеги\",\"title\":\"Как перестать нервничать\",\"pages\":200,\"year\":1960}]";
        List<Book> bookList = objectMapper
                .readValue(jsonBooksList, new TypeReference<List<Book>>() {});

        for (int i = 0; i < bookList.size(); i++) {
            System.out.println(bookList.get(i));
        }
    }

    private static void readObjectFromString() throws JsonProcessingException {
        String firstLibJson = "{\"title\":\"Горьковская библиотека\",\"books\":[{\"author\":\"Лукьяненко\",\"title\":\"Ночной дозор\",\"pages\":350,\"year\":2004},{\"author\":\"Дейл Карнеги\",\"title\":\"Как перестать нервничать\",\"pages\":200,\"year\":1960}]}";
        Library libraryFromString = objectMapper.readValue(firstLibJson, Library.class);
        System.out.println(libraryFromString);
    }

    private static void demoWriteObject() throws JsonProcessingException {
        Library firstLib = new Library(
                "Ленинская библиотека",
                new Book[]{
                        new Book("Пушкин", "Станционный смотритель", 35, 1820),
                        new Book("Лукьяненко", "Ночной дозор", 350, 2004),
                        new Book("Дейл Карнеги", "Как перестать нервничать", 200, 1960),
                },
                false
        );

        String jsonFirstLib = objectMapper.writeValueAsString(firstLib);
        System.out.println(jsonFirstLib);
    }
}
