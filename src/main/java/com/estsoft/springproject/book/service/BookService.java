package com.estsoft.springproject.book.service;

import com.estsoft.springproject.book.domain.Book;
import com.estsoft.springproject.book.domain.BookDTO;
import com.estsoft.springproject.book.repository.BookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll(Sort.by("id"));
        // SELECT * FROM book ORDER BY id;
    }

    public Book findById(String id) {
        return bookRepository.findById(id).orElse(new Book());
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
