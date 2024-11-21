package com.example.library_api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "books_authors")
@Entity
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "book_id")
    private Long bookId;
}
