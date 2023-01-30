package com.fmexperiments.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fmexperiments.application.entity.Book;
import com.fmexperiments.application.repository.BookRepository;


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
