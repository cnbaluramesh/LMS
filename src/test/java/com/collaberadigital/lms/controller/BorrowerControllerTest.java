package com.collaberadigital.lms.controller;

import com.collaberadigital.lms.model.Book;
import com.collaberadigital.lms.model.Borrower;
import com.collaberadigital.lms.repository.BookRepository;
import com.collaberadigital.lms.repository.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowerRepositoryTest {

    @Mock
    private BorrowerRepository borrowerRepository;
    @Mock
    private BookRepository bookRepository;

    @Test
    void testSaveBorrower() {
        // Given
        Borrower borrower = new Borrower(null, "test@example.com", "Test Borrower");
        Borrower savedBorrower = new Borrower(1L, "test@example.com", "Test Borrower");

        when(borrowerRepository.save(borrower)).thenReturn(savedBorrower);

        // When
        Borrower result = borrowerRepository.save(borrower);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test Borrower", result.getName());
    }

    @Test
    void testFindBorrowerById() {
        // Given
        Borrower borrower = new Borrower(1L, "test@example.com", "Test Borrower");
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));

        // When
        Optional<Borrower> result = borrowerRepository.findById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        assertEquals("Test Borrower", result.get().getName());
    }

    @Test
    void testFindAllBorrowers() {
        // Given
        Borrower borrower1 = new Borrower(1L, "borrower1@example.com", "Borrower 1");
        Borrower borrower2 = new Borrower(2L, "borrower2@example.com", "Borrower 2");
        List<Borrower> borrowers = Arrays.asList(borrower1, borrower2);

        when(borrowerRepository.findAll()).thenReturn(borrowers);

        // When
        List<Borrower> result = borrowerRepository.findAll();

        // Then
        assertEquals(2, result.size());
        assertEquals("borrower1@example.com", result.get(0).getEmail());
        assertEquals("Borrower 2", result.get(1).getName());
    }

    @Test
    void testDeleteBorrower() {
        // Given
        Borrower borrower = new Borrower(1L, "test@example.com", "Test Borrower");

        doNothing().when(borrowerRepository).delete(borrower);

        // When
        assertDoesNotThrow(() -> borrowerRepository.delete(borrower));

        // Then
        verify(borrowerRepository, times(1)).delete(borrower);
    }

    @Test
    void testBorrowBook() {
        // Given
        Borrower borrower = new Borrower(1L, "test@example.com", "Test Borrower");
        Book book = new Book(1L, "1234567890", "Test Book", "Test Author", null);

        lenient().when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        lenient().when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        lenient().when(bookRepository.save(book)).thenReturn(book);

        // When
        book.setBorrower(borrower);
        Book updatedBook = bookRepository.save(book);

        // Then
        assertNotNull(updatedBook.getBorrower());
        assertEquals(borrower, updatedBook.getBorrower());
    }
}