package com.collaberadigital.lms.dto;

import jakarta.validation.constraints.NotBlank;

public record BookDto(
        Long id,
        @NotBlank(message = "ISBN cannot be blank") String isbn,
        @NotBlank(message = "Title cannot be blank") String title,
        @NotBlank(message = "Author cannot be blank") String author,
        Long borrowerId
) {
    public BookDto withBorrower(Long borrowerId) {
        return new BookDto(this.id, this.isbn, this.title, this.author, borrowerId);
    }
}
