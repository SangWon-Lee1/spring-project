package com.estsoft.springproject.service;

import com.estsoft.springproject.book.domain.Book;
import com.estsoft.springproject.book.repository.BookRepository;
import com.estsoft.springproject.book.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("1", "Test Book", "Author Name");
    }

    @Test
    void testFindAll() {
        // given
        List<Book> books = List.of(new Book("1", "Test Book", "Author"));

        when(bookRepository.findAll(Sort.by("id"))).thenReturn(books);

        // when
        List<Book> result = bookService.findAll();

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getName());
    }

    @Test
    void testFindById_Found() {
        // given
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        // when
        Book result = bookService.findById("1");

        // then
        assertEquals("1", result.getId());
        assertEquals("Test Book", result.getName());
        verify(bookRepository, times(1)).findById("1");
    }

    @Test
    void testSaveBook() {
        // given
        when(bookRepository.save(book)).thenReturn(book);

        // when
        Book result = bookService.saveBook(book);

        // then
        assertEquals("Test Book", result.getName());
        verify(bookRepository, times(1)).save(book);
    }
}