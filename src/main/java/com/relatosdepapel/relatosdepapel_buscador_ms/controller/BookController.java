package com.relatosdepapel.relatosdepapel_buscador_ms.controller;

import com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.BookQueryResponse;
import com.relatosdepapel.relatosdepapel_buscador_ms.entity.Book;
import com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.CreateBookRequest;
import com.relatosdepapel.relatosdepapel_buscador_ms.service.BookServiceElastic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Slf4j
public class BookController {
    private final BookServiceElastic service;
//    public BookController(BookService service) {
//        this.service = service;
//    }
    @GetMapping
    public ResponseEntity<BookQueryResponse> getBooks(
            @RequestHeader Map<String, String> headers,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false, defaultValue = "false") Boolean aggregate) {

        //log.info("headers: {}", headers);
        BookQueryResponse books = service.getBooks(author, description, titulo, categoria, aggregate);
        return ResponseEntity.ok(books);
    }
//    public List<Book> getAllBooks() {
//        return service.getAllBooks();
//    }
    @PostMapping
    public Book createBook(@RequestBody CreateBookRequest book) {
        return service.createBook(book);
    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
//        Optional<Book> updated = service.updateBook(id, book);
//        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//    @PatchMapping("/{id}")
//    public ResponseEntity<Book> partialUpdateBook(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
//        Optional<Book> updated = service.partialUpdateBook(id, updates);
//        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!service.removeBook(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        Book value= service.getBook(id);
        return value;   
    }
    
//    @GetMapping("/search")
//    public List<Book> searchBooks(
//            @RequestParam(required = false) String author,
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) String type,
//            @RequestParam(required = false) String editorial,
//            @RequestParam(required = false) String idioma,
//            @RequestParam(required = false) String categoria,
//            @RequestParam(required = false) Integer valoracion,
//            @RequestParam(required = false) Boolean active
//    )
//
//    {
//        return service.searchBooks(author, title, type, editorial, idioma, categoria, valoracion, active);
//    }
}
