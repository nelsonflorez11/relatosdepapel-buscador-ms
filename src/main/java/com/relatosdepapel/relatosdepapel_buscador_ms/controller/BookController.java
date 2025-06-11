package com.relatosdepapel.relatosdepapel_buscador_ms.controller;

import com.relatosdepapel.relatosdepapel_buscador_ms.entity.Book;
import com.relatosdepapel.relatosdepapel_buscador_ms.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;
    public BookController(BookService service) {
        this.service = service;
    }
    @GetMapping
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return service.createBook(book);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Optional<Book> updated = service.updateBook(id, book);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Book> partialUpdateBook(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Book> updated = service.partialUpdateBook(id, updates);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!service.deleteBook(id)) {
            return ResponseEntity.notFound().build();
        }
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
        return service.searchBooks(author, title, type, editorial, idioma, categoria, valoracion, active);
    }
}
