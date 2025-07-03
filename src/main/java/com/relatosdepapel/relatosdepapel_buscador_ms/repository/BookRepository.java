package com.relatosdepapel.relatosdepapel_buscador_ms.repository;

import com.relatosdepapel.relatosdepapel_buscador_ms.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
//    @Query("SELECT b FROM Book b WHERE " +
//            "(:author IS NULL OR b.author = :author) AND " +
//            "(:title IS NULL OR b.title = :title) AND " +
//            "(:type IS NULL OR b.type = :type) AND " +
//            "(:editorial IS NULL OR b.editorial = :editorial) AND " +
//            "(:idioma IS NULL OR b.idioma = :idioma) AND " +
//            "(:categoria IS NULL OR b.categoria = :categoria) AND " +
//            "(:valoracion IS NULL OR b.valoracion = :valoracion) AND " +
//            "(:active IS NULL OR b.active = :active)")
//    List<Book> findByFilters(
//            @Param("author") String author,
//            @Param("title") String title,
//            @Param("type") String type,
//            @Param("editorial") String editorial,
//            @Param("idioma") String idioma,
//            @Param("categoria") String categoria,
//            @Param("valoracion") Integer valoracion,
//            @Param("active") Boolean active
//    );
}
