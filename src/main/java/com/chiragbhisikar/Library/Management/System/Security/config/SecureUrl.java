package com.chiragbhisikar.Library.Management.System.Security.config;

import java.util.List;

public class SecureUrl {
    public static final List<String> SECURED_URLS = List.of(
// user
            "/api/user/login",
            "/api/user/register",
//  categories
            "/api/categories",
            "/api/categories/{categoryId}",
            "/api/categories/byName",
//  languages
            "/api/languages",
            "/api/languages/{languageId}",
            "/api/languages/byName",
// authors
            "/api/authors",
            "/api/authors/{authorId}",
            "/api/authors/byName",
// publications
            "/api/publications",
            "/api/publications/{publicationId}",
            "/api/publications/byName",
// books
            "/api/books",
            "/api/books/{bookId}",
            "/api/books/search",

// Public folder
            "/images/**"
    );

}

