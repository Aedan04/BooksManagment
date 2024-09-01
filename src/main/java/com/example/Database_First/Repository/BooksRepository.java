package com.example.Database_First.Repository;

import com.example.Database_First.Model.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, Long> {

    List<BooksEntity> findByGenreContainingIgnoreCase(String genre);
    List<BooksEntity> findByAuthorNameContainingIgnoreCase(String authorName);
    List<BooksEntity> findByBookNameContainingIgnoreCase(String bookName);
}
