package com.relatosdepapel.relatosdepapel_buscador_ms.service;

import com.relatosdepapel.relatosdepapel_buscador_ms.entity.jpa.BookJPA;
import com.relatosdepapel.relatosdepapel_buscador_ms.repository.jpa.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookJPA> getAllBooks() {
        return repository.findAll();
    }

    public BookJPA getById(Long id) {
        BookJPA book= null;
    	 Optional<BookJPA> optionalBook = repository.findById(id);
         if (!optionalBook.isEmpty()) {
             book= optionalBook.get();
         }
         return book;
    }

    public BookJPA createBook(BookJPA book) {
        return repository.save(book);
    }

    public Optional<BookJPA> updateBook(Long id, BookJPA book) {
        return repository.findById(id)
                .map(existing -> {
                    book.setISBN(id);
                    return repository.save(book);
                });
    }

    public Optional<BookJPA> partialUpdateBook(Long id, Map<String, Object> updates) {
        Optional<BookJPA> optionalBook = repository.findById(id);
        if (optionalBook.isEmpty()) {
            return Optional.empty();
        }
        BookJPA book = optionalBook.get();
        updates.forEach((key, value) -> {
            try {
                var field = BookJPA.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(book, value);
            } catch (Exception ignored) {}
        });
        return Optional.of(repository.save(book));
    }

    public boolean deleteBook(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

//    public List<Book> searchBooks(String author, String title, String type, String editorial, String idioma, String categoria, Integer valoracion, Boolean active) {
//        return repository.findByFilters(author, title, type, editorial, idioma, categoria, valoracion, active);
//    }
}
