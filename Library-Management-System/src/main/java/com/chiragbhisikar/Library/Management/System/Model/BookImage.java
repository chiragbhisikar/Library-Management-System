package com.chiragbhisikar.Library.Management.System.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_images")
public class BookImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "FilePath Could Not Be Null !")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @NotNull(message = "Book Could Not Be Null !")
    private Book book;
}

