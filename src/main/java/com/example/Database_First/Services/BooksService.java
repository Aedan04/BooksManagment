package com.example.Database_First.Services;

import com.example.Database_First.Model.AudiencesTypeEntity;
import com.example.Database_First.Model.BooksEntity;
import com.example.Database_First.Repository.AudiencesTypeRepository;
import com.example.Database_First.Repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AudiencesTypeRepository audiencesTypeRepository;

    public BooksEntity addBook(String bookName, String authorName, int publishYear, String genre, Long audienceTypeId) {
        AudiencesTypeEntity audienceType = audiencesTypeRepository.findById(audienceTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid audience type ID"));
        BooksEntity newBook = new BooksEntity(bookName, authorName, publishYear, genre, audienceType);
        return booksRepository.save(newBook);
    }

    public BooksEntity updateBook(Long id, String bookName, String authorName, int publishYear, String genre, Long audienceTypeId) {
        AudiencesTypeEntity audienceType = audiencesTypeRepository.findById(audienceTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid audience type ID"));
        BooksEntity existingBook = booksRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + id + " does not exist."));
        existingBook.setBookName(bookName);
        existingBook.setAuthorName(authorName);
        existingBook.setPublishYear(publishYear);
        existingBook.setGenre(genre);
        existingBook.setAudienceType(audienceType);
        return booksRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        if (!booksRepository.existsById(id)) {
            throw new IllegalArgumentException("Book with ID " + id + " does not exist.");
        }
        booksRepository.deleteById(id);
    }

    public Page<BooksEntity> getBooksByBookNameWithPagination(String bookName, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return booksRepository.findByBookNameContainingIgnoreCase(bookName, pageable);
    }

    public Page<BooksEntity> getBooksByGenreWithPagination(String genre, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return booksRepository.findByGenreContainingIgnoreCase(genre, pageable);
    }

    public Page<BooksEntity> getBooksByAuthorNameWithPagination(String authorName, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return booksRepository.findByAuthorNameContainingIgnoreCase(authorName, pageable);
    }

    public List<BooksEntity> getAllBooks(int page, int size, String sortBy, String sortOrder, String sortMethod) {
        List<BooksEntity> books = booksRepository.findAll();

        // Apply sorting
        Comparator<BooksEntity> comparator = getComparator(sortBy, sortOrder);
        switch (sortMethod.toLowerCase()) {
            case "bubble":
                books = bubbleSort(books, comparator);
                break;
            case "quick":
                books = quickSort(books, comparator);
                break;
            case "merge":
                books = mergeSort(books, comparator);
                break;
            case "selection":
                books = selectionSort(books, comparator);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort method: " + sortMethod);
        }

        // Apply pagination
        int fromIndex = Math.min(page * size, books.size());
        int toIndex = Math.min(fromIndex + size, books.size());
        return books.subList(fromIndex, toIndex);
    }

    public List<BooksEntity> searchBooks(String bookName, String genre, String authorName, String sortBy, String sortOrder, String sortMethod) {
        List<BooksEntity> books = booksRepository.findByBookNameContainingIgnoreCaseAndGenreContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(bookName, genre, authorName);

        // Apply sorting
        Comparator<BooksEntity> comparator = getComparator(sortBy, sortOrder);
        switch (sortMethod.toLowerCase()) {
            case "bubble":
                books = bubbleSort(books, comparator);
                break;
            case "quick":
                books = quickSort(books, comparator);
                break;
            case "merge":
                books = mergeSort(books, comparator);
                break;
            case "selection":
                books = selectionSort(books, comparator);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort method: " + sortMethod);
        }

        return books;
    }

    private Comparator<BooksEntity> getComparator(String sortBy, String sortOrder) {
        Comparator<BooksEntity> comparator;
        switch (sortBy.toLowerCase()) {
            case "id":
                comparator = Comparator.comparing(BooksEntity::getId);
                break;
            case "bookname":
                comparator = Comparator.comparing(BooksEntity::getBookName);
                break;
            case "authorname":
                comparator = Comparator.comparing(BooksEntity::getAuthorName);
                break;
            case "publishyear":
                comparator = Comparator.comparing(BooksEntity::getPublishYear);
                break;
            case "genre":
                comparator = Comparator.comparing(BooksEntity::getGenre);
                break;
            case "audiencetype":
                comparator = Comparator.comparing(book -> book.getAudienceType() != null ? book.getAudienceType().getId() : 0);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort column: " + sortBy);
        }
        return "desc".equalsIgnoreCase(sortOrder) ? comparator.reversed() : comparator;
    }

    private List<BooksEntity> bubbleSort(List<BooksEntity> books, Comparator<BooksEntity> comparator) {
        // Bubble sort implementation
        int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(books.get(j), books.get(j + 1)) > 0) {
                    BooksEntity temp = books.get(j);
                    books.set(j, books.get(j + 1));
                    books.set(j + 1, temp);
                }
            }
        }
        return books;
    }

    private List<BooksEntity> quickSort(List<BooksEntity> books, Comparator<BooksEntity> comparator) {
        // Quick sort implementation
        if (books.size() <= 1) return books;
        BooksEntity pivot = books.get(books.size() / 2);
        List<BooksEntity> less = books.stream().filter(book -> comparator.compare(book, pivot) < 0).collect(Collectors.toList());
        List<BooksEntity> equal = books.stream().filter(book -> comparator.compare(book, pivot) == 0).collect(Collectors.toList());
        List<BooksEntity> greater = books.stream().filter(book -> comparator.compare(book, pivot) > 0).collect(Collectors.toList());
        List<BooksEntity> sortedBooks = quickSort(less, comparator);
        sortedBooks.addAll(equal);
        sortedBooks.addAll(quickSort(greater, comparator));
        return sortedBooks;
    }

    private List<BooksEntity> mergeSort(List<BooksEntity> books, Comparator<BooksEntity> comparator) {
        // Merge sort implementation
        if (books.size() <= 1) return books;
        int mid = books.size() / 2;
        List<BooksEntity> left = books.subList(0, mid);
        List<BooksEntity> right = books.subList(mid, books.size());
        return merge(mergeSort(left, comparator), mergeSort(right, comparator), comparator);
    }

    private List<BooksEntity> merge(List<BooksEntity> left, List<BooksEntity> right, Comparator<BooksEntity> comparator) {
        List<BooksEntity> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }
        result.addAll(left.subList(i, left.size()));
        result.addAll(right.subList(j, right.size()));
        return result;
    }

    private List<BooksEntity> selectionSort(List<BooksEntity> books, Comparator<BooksEntity> comparator) {
        // Selection sort implementation
        int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(books.get(j), books.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            BooksEntity temp = books.get(i);
            books.set(i, books.get(minIndex));
            books.set(minIndex, temp);
        }
        return books;
    }


};
