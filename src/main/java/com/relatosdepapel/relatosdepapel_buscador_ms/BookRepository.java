package com.relatosdepapel.relatosdepapel_buscador_ms;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}