package com.collaberadigital.lms.repository;

import com.collaberadigital.lms.dto.BookDto;
import com.collaberadigital.lms.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Custom query methods can be added here if needed

    Optional<Book> findByIsbn(String isbn);
    List<Book> findByAuthor(String author);

}