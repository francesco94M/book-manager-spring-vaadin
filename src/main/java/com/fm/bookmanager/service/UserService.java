package com.fm.bookmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fm.bookmanager.entity.Book;
import com.fm.bookmanager.repository.BookRepository;


@Service
public class BookService {

    private final BookRepository repository;


    public BookService(BookRepository repository)
    {
        this.repository = repository;
    }

    public Optional<Book> get(Long id) {
        return repository.findById(id);
    }

    public Book update(Book entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Book> list()
    {
        return repository.findAll();
    }

    public Page<Book> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Book> list(Pageable pageable, Specification<Book> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
