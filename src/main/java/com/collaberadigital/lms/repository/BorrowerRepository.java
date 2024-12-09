package com.collaberadigital.lms.repository;

import com.collaberadigital.lms.dto.BorrowerDto;
import com.collaberadigital.lms.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    // Custom query methods can be added here if needed

    boolean existsByEmail(String email);

}