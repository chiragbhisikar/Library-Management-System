package com.chiragbhisikar.Library.Management.System.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String isbn;
    @NotNull
    private int totalPages;
    @NotNull
    private int edition;
    @NotNull
    private Year publicationYear;
    @NotNull
    private int perDayPenalty;
    @NotNull
    private int noOfAvailableBooks;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull(message = "Author cannot be null")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    @NotNull(message = "Publication cannot be null")
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Category cannot be null")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "language_id")
    @NotNull(message = "Language cannot be null")
    private Language language;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private eBook ebook;

//    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Copy> copies = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<eBook> eBooks = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookImage> images;
}