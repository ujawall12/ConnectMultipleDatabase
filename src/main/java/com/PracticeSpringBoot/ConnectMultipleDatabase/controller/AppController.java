package com.PracticeSpringBoot.ConnectMultipleDatabase.controller;

import com.PracticeSpringBoot.ConnectMultipleDatabase.bookRepository.BookRepository;
import com.PracticeSpringBoot.ConnectMultipleDatabase.modelBook.Book;
import com.PracticeSpringBoot.ConnectMultipleDatabase.modelUser.User;
import com.PracticeSpringBoot.ConnectMultipleDatabase.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppController {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @PostMapping("/data")
    public void addData2Db() {
        userRepository.saveAll(Stream.of(
                new User(101, "user101"),
                new User(102, "user102"))
                .toList());

        bookRepository.saveAll(Stream.of(
                new Book(201, "book201"),
                new Book(202, "book202"))
                .toList());
    }

    @GetMapping("/userData")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/bookData")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

}
