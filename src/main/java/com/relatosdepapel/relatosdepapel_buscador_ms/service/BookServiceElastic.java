package com.relatosdepapel.relatosdepapel_buscador_ms.service;


import com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.CreateBookRequest;
import com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.BookQueryResponse;
import com.relatosdepapel.relatosdepapel_buscador_ms.entity.Book;

public interface BookServiceElastic {

    BookQueryResponse getBooks(String author, String description, String titulo, String categoria, String formato, Float minPrice, Float maxPrice, Boolean aggregate);
    Book getBook(Long Id);

    Boolean removeBook(Long Id);

    Book createBook(CreateBookRequest request);

}