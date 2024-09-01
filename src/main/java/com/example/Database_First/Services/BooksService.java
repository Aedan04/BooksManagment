package com.example.Database_First.Service;

import com.example.Database_First.Model.BooksEntity;
import com.example.Database_First.Repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    // Create a new book
    public BooksEntity createBook(BooksEntity book) {
        return booksRepository.save(book);
    }

    // Retrieve all books with pagination and sorting
    public Page<BooksEntity> getAllBooks(PageRequest pageRequest, String algorithm) {
        // Implement the sorting algorithm logic here if necessary
        // For simplicity, let's assume that the database sorting will be used
        return booksRepository.findAll(pageRequest);
    }

    // Retrieve a book by ID
    public Optional<BooksEntity> getBookById(Long id) {
        return booksRepository.findById(id);
    }

    // Search books by genre, authorName, or bookName
    public List<BooksEntity> searchBooks(String genre, String authorName, String bookName) {
        // Add custom search logic here if needed
        if (genre != null) {
            return booksRepository.findByGenreContainingIgnoreCase(genre);
        } else if (authorName != null) {
            return booksRepository.findByAuthorNameContainingIgnoreCase(authorName);
        } else if (bookName != null) {
            return booksRepository.findByBookNameContainingIgnoreCase(bookName);
        } else {
            return booksRepository.findAll();
        }
    }

    // Update a book
    public BooksEntity updateBook(Long id, BooksEntity bookDetails) {
        BooksEntity book = booksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));

        book.setBookName(bookDetails.getBookName());
        book.setAuthorName(bookDetails.getAuthorName());
        book.setPublishYear(bookDetails.getPublishYear());
        book.setGenre(bookDetails.getGenre());
        book.setAudienceType(bookDetails.getAudienceType());

        return booksRepository.save(book);
    }

    // Delete a book
    public void deleteBook(Long id) {
        BooksEntity book = booksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        booksRepository.delete(book);
    }
}
