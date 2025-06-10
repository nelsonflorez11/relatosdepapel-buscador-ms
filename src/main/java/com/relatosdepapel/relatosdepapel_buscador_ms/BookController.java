package com.relatosdepapel.relatosdepapel_buscador_ms;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return repository.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return repository.findById(id)
                .map(existing -> {
                    book.setISBN(id);
                    return ResponseEntity.ok(repository.save(book));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> partialUpdateBook(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Book> optionalBook = repository.findById(id);
        if (optionalBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Book book = optionalBook.get();
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Book.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, book, value);
            }
        });
        return ResponseEntity.ok(repository.save(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Book> searchBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) String idioma,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Integer valoracion,
            @RequestParam(required = false) Boolean active
    ) {
        return repository.findByFilters(author, title, type, editorial, idioma, categoria, valoracion, active);
    }
}