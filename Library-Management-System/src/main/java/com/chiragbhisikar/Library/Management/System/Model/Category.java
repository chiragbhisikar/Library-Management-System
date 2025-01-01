package com.chiragbhisikar.Library.Management.System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private boolean is_deleted = false;


//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private Set<Book> category = new HashSet<>();
}