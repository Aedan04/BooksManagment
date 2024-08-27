package com.example.Database_First.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BooksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bookName;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private int publishYear;

    @Column(nullable = false)
    private String genre;

    @ManyToOne(fetch = FetchType.EAGER)  // Ensure it's eager to always load the audience type
    @JoinColumn(name = "audience_type_id", nullable = false)
    @JsonIgnoreProperties("books")  // Avoid serialization loop
    private AudiencesTypeEntity audienceType;

    // Constructors, getters, and setters

    public BooksEntity() {}

    public BooksEntity(String bookName, String authorName, int publishYear, String genre, AudiencesTypeEntity audienceType) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.publishYear = publishYear;
        this.genre = genre;
        this.audienceType = audienceType;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public int getPublishYear() { return publishYear; }
    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public AudiencesTypeEntity getAudienceType() { return audienceType; }
    public void setAudienceType(AudiencesTypeEntity audienceType) { this.audienceType = audienceType; }
}