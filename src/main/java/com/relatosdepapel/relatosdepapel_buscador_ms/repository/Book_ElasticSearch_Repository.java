package com.relatosdepapel.relatosdepapel_buscador_ms.repository;

import com.relatosdepapel.relatosdepapel_buscador_ms.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface Book_ElasticSearch_Repository extends ElasticsearchRepository<Book, String> {

    //List<Book> findByAuthor(String author);

    Optional<Book> findById(String id);

    Book save(Book libro);

    void delete(Book libro);

    //List<Book> findAll();
}