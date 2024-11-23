package com.estsoft.springproject.repository;

import com.estsoft.springproject.book.domain.Book;
import com.estsoft.springproject.book.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("1", "Test Book", "Test Author");
    }

    @Test
    void testSaveBook() {
        // given
        bookRepository.save(book);

        // when
        Optional<Book> foundBook = bookRepository.findById("1");

        // then
        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getName());
        assertEquals("Test Author", foundBook.get().getAuthor());
    }

    @Test
    void testFindById_NotFound() {
        // when
        Optional<Book> foundBook = bookRepository.findById("999");

        // then
        assertTrue(foundBook.isEmpty());
    }
}
