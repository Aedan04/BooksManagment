package com.example.Database_First.Controller;

import com.example.Database_First.Model.BooksEntity;
import com.example.Database_First.Service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    @Autowired
    private BooksService booksService;

    // Create a new book
    @PostMapping
    public ResponseEntity<BooksEntity> createBook(@RequestBody BooksEntity book) {
        BooksEntity createdBook = booksService.createBook(book);
        return ResponseEntity.ok(createdBook);
    }

    // Retrieve books with pagination and sorting
    @GetMapping
    public ResponseEntity<Page<BooksEntity>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "quick") String algorithm) {

        Page<BooksEntity> books = booksService.getAllBooks(PageRequest.of(page, size, Sort.by(sort)), algorithm);
        return ResponseEntity.ok(books);
    }

    // Retrieve a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BooksEntity> getBookById(@PathVariable Long id) {
        BooksEntity book = booksService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        return ResponseEntity.ok(book);
    }

    // Search books by genre
    @GetMapping("/search")
    public ResponseEntity<List<BooksEntity>> searchBooks(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String bookName) {

        List<BooksEntity> books = booksService.searchBooks(genre, authorName, bookName);
        return ResponseEntity.ok(books);
    }

    // Update a book
    @PutMapping("/{id}")
    public ResponseEntity<BooksEntity> updateBook(@PathVariable Long id, @RequestBody BooksEntity bookDetails) {
        BooksEntity updatedBook = booksService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        booksService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
