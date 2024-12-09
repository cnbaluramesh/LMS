package com.collaberadigital.lms.controller;

import com.collaberadigital.lms.dto.BookDto;
import com.collaberadigital.lms.model.Book;
import com.collaberadigital.lms.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Registers a new book.
     *
     * @param bookDto the book data to register
     * @return ResponseEntity with the created book or an error status
     */
    @PostMapping
    public ResponseEntity<BookDto> registerBook(@RequestBody BookDto bookDto) {
        logger.info("Request to register book: {}", bookDto);

        // Convert DTO to Entity
        Book book = new Book(null, bookDto.isbn(), bookDto.title(), bookDto.author(), null);
        try {
            Book savedBook = bookRepository.save(book);

            // Convert Entity back to DTO
            BookDto savedBookDto = new BookDto(savedBook.getId(), savedBook.getIsbn(), savedBook.getTitle(), savedBook.getAuthor(), null);
            return ResponseEntity.status(201).body(savedBookDto);
        } catch (IllegalArgumentException e) {
            logger.error("Error registering book: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves all books.
     *
     * @return ResponseEntity containing a list of all books
     */
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        logger.info("Request to fetch all books");

        List<BookDto> books = bookRepository.findAll().stream()
                .map(book -> new BookDto(book.getId(), book.getIsbn(), book.getTitle(), book.getAuthor(), null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    /**
     * Retrieves details of a specific book by its ID.
     *
     * @param bookId the ID of the book
     * @return ResponseEntity containing the book details or a not-found status
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBookDetails(@PathVariable Long bookId) {
        logger.info("Request to fetch book with ID: {}", bookId);

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            BookDto bookDto = new BookDto(book.getId(), book.getIsbn(), book.getTitle(), book.getAuthor(), null);
            return ResponseEntity.ok(bookDto);
        } else {
            logger.error("Book with ID {} not found", bookId);
            return ResponseEntity.notFound().build();
        }
    }
}
