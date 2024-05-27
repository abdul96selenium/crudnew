package com.example.repository;
// Java Program to Illustrate BookRepo File
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Book;

public interface BookRepo
        extends MongoRepository<Book, Integer> {
}