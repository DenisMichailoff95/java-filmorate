package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmorateApplication {
    public static void main(String[] args) {
        System.setProperty("server.port", "8080");
        SpringApplication.run(FilmorateApplication.class, args);
    }
}