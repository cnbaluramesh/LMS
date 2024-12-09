package com.collaberadigital.lms.controller;

import com.collaberadigital.lms.dto.BorrowerDto;
import com.collaberadigital.lms.model.Borrower;
import com.collaberadigital.lms.repository.BorrowerRepository;
import com.collaberadigital.lms.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {
    private static final Logger logger = LoggerFactory.getLogger(BorrowerController.class);

    private final BorrowerRepository borrowerRepository;
    private final BookRepository bookRepository;

    public BorrowerController(BorrowerRepository borrowerRepository, BookRepository bookRepository) {
        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<BorrowerDto> registerBorrower(@RequestBody BorrowerDto borrowerDto) {
        logger.info("Request to create borrower: {}", borrowerDto);

        if (borrowerRepository.existsByEmail(borrowerDto.email())) {
            logger.error("Borrower with email {} already exists", borrowerDto.email());
            return ResponseEntity.badRequest().build();
        }

        Borrower borrower = new Borrower(null, borrowerDto.email(), borrowerDto.name());
        Borrower savedBorrower = borrowerRepository.save(borrower);
        BorrowerDto savedBorrowerDto = new BorrowerDto(savedBorrower.getId(), savedBorrower.getEmail(), savedBorrower.getName());

        return ResponseEntity.status(201).body(savedBorrowerDto);
    }

    @PostMapping("/{borrowerId}/borrow/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        logger.info("Request to borrow book with borrowerId: {}, bookId: {}", borrowerId, bookId);

        Optional<Borrower> borrowerOptional = borrowerRepository.findById(borrowerId);
        if (borrowerOptional.isEmpty()) {
            logger.error("Borrower with ID {} not found", borrowerId);
            return ResponseEntity.badRequest().body("Borrower not found");
        }

        bookRepository.findById(bookId).ifPresentOrElse(
            book -> {
                if (book.getBorrower() != null) {
                    throw new IllegalStateException("Book is already borrowed");
                }
                book.setBorrower(borrowerOptional.get());
                bookRepository.save(book);
            },
            () -> {
                throw new IllegalStateException("Book not found");
            }
        );

        return ResponseEntity.ok("Book borrowed successfully");
    }

    @PostMapping("/{borrowerId}/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        logger.info("Request to return book with borrowerId: {}, bookId: {}", borrowerId, bookId);

        bookRepository.findById(bookId).ifPresentOrElse(
            book -> {
                if (book.getBorrower() == null || !book.getBorrower().getId().equals(borrowerId)) {
                    throw new IllegalStateException("Book is not borrowed by this borrower");
                }
                book.setBorrower(null);
                bookRepository.save(book);
            },
            () -> {
                throw new IllegalStateException("Book not found");
            }
        );

        return ResponseEntity.ok("Book returned successfully");
    }

    @GetMapping
    public ResponseEntity<List<BorrowerDto>> getAllBorrowers() {
        logger.info("Request to get all borrowers");

        List<BorrowerDto> borrowers = borrowerRepository.findAll().stream()
                .map(borrower -> new BorrowerDto(borrower.getId(), borrower.getEmail(), borrower.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(borrowers);
    }

    @GetMapping("/{borrowerId}")
    public ResponseEntity<BorrowerDto> getBorrowerDetails(@PathVariable Long borrowerId) {
        logger.info("Request to get borrower with ID: {}", borrowerId);

        return borrowerRepository.findById(borrowerId)
                .map(borrower -> new BorrowerDto(borrower.getId(), borrower.getEmail(), borrower.getName()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.error("Borrower with ID {} not found", borrowerId);
                    return ResponseEntity.notFound().build();
                });
    }
}
