package com.example.Database_First.Controller;

import com.example.Database_First.Model.BooksEntity;
import com.example.Database_First.Services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Books")
public class BooksController {

    @Autowired
    private BooksService booksService;

    @PostMapping("/add")
        public ResponseEntity<BooksEntity> addBook(@RequestParam String bookName,
                                               @RequestParam String authorName,
                                               @RequestParam int publishYear,
                                               @RequestParam String genre,
                                               @RequestParam Long audienceTypeId) {
        BooksEntity book = booksService.addBook(bookName, authorName, publishYear, genre, audienceTypeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BooksEntity> updateBook(@PathVariable Long id,
                                                  @RequestParam String bookName,
                                                  @RequestParam String authorName,
                                                  @RequestParam int publishYear,
                                                  @RequestParam String genre,
                                                  @RequestParam Long audienceTypeId) {
        try {
            BooksEntity updatedBook = booksService.updateBook(id, bookName, authorName, publishYear, genre, audienceTypeId);
            return ResponseEntity.ok(updatedBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            booksService.deleteBook(id);
            return ResponseEntity.ok("Book deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/name/paginated")
    public Page<BooksEntity> searchBooksByBookNameWithPagination(@RequestParam String bookName,
                                                                 @RequestParam int page,
                                                                 @RequestParam int size,
                                                                 @RequestParam(defaultValue = "bookName") String sortBy,
                                                                 @RequestParam(defaultValue = "asc") String sortOrder) {
        return booksService.getBooksByBookNameWithPagination(bookName, page, size, sortBy, sortOrder);
    }

    @GetMapping("/search/genre/paginated")
    public Page<BooksEntity> searchBooksByGenreWithPagination(@RequestParam String genre,
                                                              @RequestParam int page,
                                                              @RequestParam int size,
                                                              @RequestParam(defaultValue = "genre") String sortBy,
                                                              @RequestParam(defaultValue = "asc") String sortOrder) {
        return booksService.getBooksByGenreWithPagination(genre, page, size, sortBy, sortOrder);
    }

    @GetMapping("/search/author/paginated")
    public Page<BooksEntity> searchBooksByAuthorNameWithPagination(@RequestParam String authorName,
                                                                   @RequestParam int page,
                                                                   @RequestParam int size,
                                                                   @RequestParam(defaultValue = "authorName") String sortBy,
                                                                   @RequestParam(defaultValue = "asc") String sortOrder) {
        return booksService.getBooksByAuthorNameWithPagination(authorName, page, size, sortBy, sortOrder);
    }

    @GetMapping
    public ResponseEntity<List<BooksEntity>> getBooks(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "bubble") String sortMethod) {
        List<BooksEntity> books = booksService.getAllBooks(page, size, sortBy, sortOrder, sortMethod);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BooksEntity>> searchBooks(
            @RequestParam(required = false, defaultValue = "") String bookName,
            @RequestParam(required = false, defaultValue = "") String genre,
            @RequestParam(required = false, defaultValue = "") String authorName,
            @RequestParam(defaultValue = "bookName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "bubble") String sortMethod) {
        List<BooksEntity> books = booksService.searchBooks(bookName, genre, authorName, sortBy, sortOrder, sortMethod);
        return ResponseEntity.ok(books);
    }
}
