package com.collaberadigital.lms.controller;

import com.collaberadigital.lms.dto.BookDto;
import com.collaberadigital.lms.exception.ResourceNotFoundException;
import com.collaberadigital.lms.model.Book;
import com.collaberadigital.lms.model.Borrower;
import com.collaberadigital.lms.repository.BookRepository;
import com.collaberadigital.lms.repository.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private ModelMapper modelMapper;

    private BookDto createBookDTO(String isbn, String title, String author, Long borrowerId) {
        return new BookDto(null, isbn, title, author, borrowerId);
    }

    @Test
    void testSaveBook() {
        // Given
        BookDto bookDTO = createBookDTO("978-3-16-148410-0", "Effective Java", "Joshua Bloch", null);
        Book book = new Book(1L, "978-3-16-148410-0", "Effective Java", "Joshua Bloch", null);

        // Mocking
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        Book savedBook = bookRepository.save(book);

        // Then
        assertNotNull(savedBook);
        assertEquals("978-3-16-148410-0", savedBook.getIsbn());
        assertEquals("Effective Java", savedBook.getTitle());
        assertEquals("Joshua Bloch", savedBook.getAuthor());
    }

    @Test
    void testFindAllBooks() {
        // Given
        Book book1 = new Book(1L, "978-0-13-468599-1", "Clean Code", "Robert C. Martin", null);
        Book book2 = new Book(2L, "978-1-491-95412-1", "Refactoring", "Martin Fowler", null);
        List<Book> mockBooks = List.of(book1, book2);

        // Mocking
        when(bookRepository.findAll()).thenReturn(mockBooks);

        // When
        List<Book> books = bookRepository.findAll();

        // Then
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("Clean Code", books.get(0).getTitle());
        assertEquals("Refactoring", books.get(1).getTitle());
    }

    @Test
    void testFindBookById() {
        // Given
        Book book = new Book(1L, "978-1-491-95412-1", "Refactoring", "Martin Fowler", null);

        // Mocking
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When
        Optional<Book> foundBook = bookRepository.findById(1L);

        // Then
        assertTrue(foundBook.isPresent());
        assertEquals("Refactoring", foundBook.get().getTitle());
        assertEquals("978-1-491-95412-1", foundBook.get().getIsbn());
    }

    @Test
    void testFindBookByIdNotFound() {
        // Mocking
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Book> foundBook = bookRepository.findById(999L);

        // Then
        assertFalse(foundBook.isPresent());
    }

    @Test
    void testSaveBookWithValidBorrowerId() {
        // Given
        Borrower borrower = new Borrower(1L, "john.doe@example.com", "John Doe");
        Book book = new Book(1L, "978-3-16-148410-0", "Effective Java", "Joshua Bloch", borrower);

        // Mocking
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        Optional<Borrower> optionalBorrower = borrowerRepository.findById(1L);
        assertTrue(optionalBorrower.isPresent());

        book.setBorrower(optionalBorrower.get());
        Book savedBook = bookRepository.save(book);

        // Then
        assertNotNull(savedBook);
        assertNotNull(savedBook.getBorrower());
        assertEquals("john.doe@example.com", savedBook.getBorrower().getEmail());
    }

    @Test
    void testSaveBookWithInvalidBorrowerId() {
        // Mocking
        when(borrowerRepository.findById(999L)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            Optional<Borrower> optionalBorrower = borrowerRepository.findById(999L);
            if (optionalBorrower.isEmpty()) {
                throw new ResourceNotFoundException("Borrower not found");
            }
        });

        assertEquals("Borrower not found", exception.getMessage());
    }

    @Test
    void testSaveBookWithoutBorrower() {
        // Given
        Book book = new Book(1L, "978-0-13-468599-1", "Clean Code", "Robert C. Martin", null);

        // Mocking
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        Book savedBook = bookRepository.save(book);

        // Then
        assertNotNull(savedBook);
        assertNull(savedBook.getBorrower());
    }

    @Test
    void testFindBooksByIsbn() {
        // Given
        Book book = new Book(1L, "978-0-13-468599-1", "Clean Code", "Robert C. Martin", null);

        // Mocking
        when(bookRepository.findByIsbn("978-0-13-468599-1")).thenReturn(Optional.of(book));

        // When
        Optional<Book> foundBook = bookRepository.findByIsbn("978-0-13-468599-1");

        // Then
        assertTrue(foundBook.isPresent());
        assertEquals("Clean Code", foundBook.get().getTitle());
        assertEquals("Robert C. Martin", foundBook.get().getAuthor());
        assertEquals("978-0-13-468599-1", foundBook.get().getIsbn());
    }
}
