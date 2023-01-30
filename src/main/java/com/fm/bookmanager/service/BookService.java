package com.fm.bookmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fm.bookmanager.entity.Book;
import com.fm.bookmanager.repository.implementations.BookRepository;


@Service
public class BookService extends AbstractCrudService<Book,Long, BookRepository> {

    private final BookRepository repository;


    public BookService(BookRepository repository)
    {
        super(repository);
        this.repository = repository;
    }


    public Page<Book> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Book> list(Pageable pageable, Specification<Book> filter) {
        return repository.findAll(filter, pageable);
    }



}
