package com.example.crudnew.controller;
import com.example.controller.BookController;
import com.example.model.Book;
import com.example.repository.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookRepo bookRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBook() {
        Book book = new Book(1, "Test Book", "Test Author");
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        ResponseEntity<String> response = bookController.saveBook(book);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Added Successfully", response.getBody());
    }

    @Test
    void getBooks() {
        List<Book> books = Arrays.asList(
                new Book(1, "Test Book 1", "Test Author 1"),
                new Book(2, "Test Book 2", "Test Author 2")
        );
        when(bookRepo.findAll()).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.getBooks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getBookById_Found() {
        Book book = new Book(1, "Test Book", "Test Author");
        when(bookRepo.findById(1)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = bookController.getBookById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void getBookById_NotFound() {
        when(bookRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.getBookById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateBookById_Found() {
        Book book = new Book(1, "Updated Book", "Updated Author");
        when(bookRepo.existsById(1)).thenReturn(true);
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        ResponseEntity<String> response = bookController.updateBookById(book);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book updated successfully", response.getBody());
    }

    @Test
    void updateBookById_NotFound() {
        Book book = new Book(1, "Updated Book", "Updated Author");
        when(bookRepo.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = bookController.updateBookById(book);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found", response.getBody());
    }

    @Test
    void deleteBook_Found() {
        when(bookRepo.existsById(1)).thenReturn(true);
        doNothing().when(bookRepo).deleteById(1);

        ResponseEntity<String> response = bookController.deleteBook(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted Successfully", response.getBody());
    }

    @Test
    void deleteBook_NotFound() {
        when(bookRepo.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = bookController.deleteBook(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found", response.getBody());
    }
}

