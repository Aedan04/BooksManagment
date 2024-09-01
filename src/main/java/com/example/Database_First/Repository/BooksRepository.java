package com.example.Database_First.Repository;

import com.example.Database_First.Model.BooksEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BooksRepository extends JpaRepository<BooksEntity, Long> {
    Page<BooksEntity> findByBookNameContainingIgnoreCase(String bookName, Pageable pageable);
    Page<BooksEntity> findByGenreContainingIgnoreCase(String genre, Pageable pageable);
    Page<BooksEntity> findByAuthorNameContainingIgnoreCase(String authorName, Pageable pageable);
    List<BooksEntity> findByBookNameContainingIgnoreCaseAndGenreContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(
            String bookName, String genre, String authorName);

}
