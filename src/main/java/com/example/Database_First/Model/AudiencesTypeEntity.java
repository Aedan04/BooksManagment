package com.example.Database_First.Model;

import com.example.Database_First.Model.BooksEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "audiences_type")
public class AudiencesTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String audienceType;

    @OneToMany(mappedBy = "audienceType")
    @JsonManagedReference // This will manage the serialization reference
    private Set<BooksEntity> books;

    // Constructors, getters, and setters
    public AudiencesTypeEntity(String audienceType) {
        this.audienceType = audienceType;
    }

    public AudiencesTypeEntity() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAudienceType() {
        return audienceType;
    }

    public void setAudienceType(String audienceType) {
        this.audienceType = audienceType;
    }

    public Set<BooksEntity> getBooks() {
        return books;
    }

    public void setBooks(Set<BooksEntity> books) {
        this.books = books;
    }
}
