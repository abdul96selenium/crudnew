package com.example.controller;

import com.example.model.Book;
import com.example.repository.BookRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepo repo;

    @PostMapping("/add")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> saveBook(@RequestBody Book book){
        repo.save(book);
        return new ResponseEntity<>("Added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = repo.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        Optional<Book> book = repo.findById(id);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateBookById(@RequestBody Book book) {
        if (repo.existsById(book.getId())) {
            repo.save(book);
            return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }
}
