package com.relatosdepapel.relatosdepapel_buscador_ms.service;


import com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.CreateBookRequest;
import com.relatosdepapel.relatosdepapel_buscador_ms.dto.CategoriaFacetDTO;
import com.relatosdepapel.relatosdepapel_buscador_ms.dto.FormatoFacetDTO;
import com.relatosdepapel.relatosdepapel_buscador_ms.dto.ItemFacetDTO;
import com.relatosdepapel.relatosdepapel_buscador_ms.dto.ResponseFacetDTO;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;

import com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.BookQueryResponse;
import com.relatosdepapel.relatosdepapel_buscador_ms.entity.Book;

public interface BookServiceElastic {

    BookQueryResponse getBooks(String author, String description, String titulo, String categoria, Boolean aggregate);
    Book getBook(Long Id);

    Boolean removeBook(Long Id);

    Book createBook(CreateBookRequest request);
    
    List<CategoriaFacetDTO> obtenerCategoriasConConteo() throws IOException;
    
    List<FormatoFacetDTO> obtenerFormatosConConteo() throws IOException;
    
    ResponseFacetDTO obtenerFacetsCombinados() throws IOException;

}