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
        AudiencesTypeEntity audienceType = findAudienceTypeById(audienceTypeId);
        BooksEntity newBook = new BooksEntity(bookName, authorName, publishYear, genre, audienceType);
        return booksRepository.save(newBook);
    }

    public BooksEntity updateBook(Long id, String bookName, String authorName, int publishYear, String genre, Long audienceTypeId) {
        AudiencesTypeEntity audienceType = findAudienceTypeById(audienceTypeId);
        BooksEntity existingBook = findBookById(id);
        existingBook.setBookName(bookName);
        existingBook.setAuthorName(authorName);
        existingBook.setPublishYear(publishYear);
        existingBook.setGenre(genre);
        existingBook.setAudienceType(audienceType);
        return booksRepository.save(existingBook);
    }

    public List<BooksEntity> searchBooks(String bookName, String genre, String authorName) {
        return booksRepository.findByBookNameContainingIgnoreCaseAndGenreContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(
                bookName != null ? bookName : "",
                genre != null ? genre : "",
                authorName != null ? authorName : ""
        );
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
        List<BooksEntity> sortedBooks = sortBooks(books, sortBy, sortOrder, sortMethod);

        // Apply pagination
            int fromIndex = Math.min(page * size, sortedBooks.size());
            int toIndex = Math.min(fromIndex + size, sortedBooks.size());
        return sortedBooks.subList(fromIndex, toIndex);
    }

    public List<BooksEntity> searchBooks(String bookName, String genre, String authorName, String sortBy, String sortOrder, String sortMethod) {
        List<BooksEntity> books = booksRepository.findByBookNameContainingIgnoreCaseAndGenreContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(bookName, genre, authorName);
        return sortBooks(books, sortBy, sortOrder, sortMethod);
    }

    private AudiencesTypeEntity findAudienceTypeById(Long audienceTypeId) {
        return audiencesTypeRepository.findById(audienceTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid audience type ID"));
    }

    private BooksEntity findBookById(Long id) {
        return booksRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + id + " does not exist."));
    }

    private List<BooksEntity> sortBooks(List<BooksEntity> books, String sortBy, String sortOrder, String sortMethod) {
        Comparator<BooksEntity> comparator = getComparator(sortBy, sortOrder);
        return switch (sortMethod.toLowerCase()) {
            case "bubble" -> bubbleSort(books, comparator);
            case "quick" -> quickSort(books, comparator);
            case "merge" -> mergeSort(books, comparator);
            case "selection" -> selectionSort(books, comparator);
            default -> throw new IllegalArgumentException("Invalid sort method: " + sortMethod);
        };
    }

    private Comparator<BooksEntity> getComparator(String sortBy, String sortOrder) {
        Comparator<BooksEntity> comparator = switch (sortBy.toLowerCase()) {
            case "id" -> Comparator.comparing(BooksEntity::getId);
            case "bookname" -> Comparator.comparing(BooksEntity::getBookName);
            case "authorname" -> Comparator.comparing(BooksEntity::getAuthorName);
            case "publishyear" -> Comparator.comparing(BooksEntity::getPublishYear);
            case "genre" -> Comparator.comparing(BooksEntity::getGenre);
            case "audiencetype" ->
                    Comparator.comparing(book -> book.getAudienceType() != null ? book.getAudienceType().getId() : 0);
            default -> throw new IllegalArgumentException("Invalid sort column: " + sortBy);
        };
        return "desc".equalsIgnoreCase(sortOrder) ? comparator.reversed() : comparator;
    }

    private List<BooksEntity> bubbleSort(List<BooksEntity> books, Comparator<BooksEntity> comparator) {
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
        if (books.size() <= 1) return books;
        int mid = books.size() / 2;
        List<BooksEntity> left = new ArrayList<>(books.subList(0, mid));
        List<BooksEntity> right = new ArrayList<>(books.subList(mid, books.size()));
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
}
