

-- lms.books definition

CREATE TABLE `books` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `author` varchar(255) NOT NULL,
                         `isbn` varchar(255) NOT NULL,
                         `title` varchar(255) NOT NULL,
                         `borrower_id` bigint DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UKkibbepcitr0a3cpk3rfr7nihn` (`isbn`),
                         KEY `FK_Book_Borrower` (`borrower_id`),
                         CONSTRAINT `FK_Book_Borrower` FOREIGN KEY (`borrower_id`) REFERENCES `borrowers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- lms.borrowers definition

CREATE TABLE `borrowers` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `email` varchar(255) NOT NULL,
                             `name` varchar(255) NOT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `UKht682rer8p1t67vs9f0ocm8cs` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO Borrowers (email, name)
VALUES
    ('john.doe@example.com', 'John Doe'),
    ('jane.doe@example.com', 'Jane Doe'),
    ('michael.smith@example.com', 'Michael Smith'),
    ('emma.watson@example.com', 'Emma Watson'),
    ('david.johnson@example.com', 'David Johnson');


INSERT INTO Books (isbn, title, author, borrower_id)
VALUES
    ('978-3-16-148410-0', 'Effective Java', 'Joshua Bloch', 1),
    ('978-0-13-468599-1', 'Clean Code', 'Robert C. Martin', 2),
    ('978-1-491-95412-1', 'Refactoring', 'Martin Fowler', NULL),
    ('978-1-59327-584-6', 'Eloquent JavaScript', 'Marijn Haverbeke', NULL),
    ('978-1-59327-757-4', 'Automate the Boring Stuff with Python', 'Al Sweigart', 3),
    ('978-1-4919-1889-0', 'Designing Data-Intensive Applications', 'Martin Kleppmann', NULL);
