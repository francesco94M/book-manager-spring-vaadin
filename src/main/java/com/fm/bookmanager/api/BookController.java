package com.fm.bookmanager.restControllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fm.bookmanager.entity.Book;
import com.fm.bookmanager.repository.BookRepository;
import com.fm.bookmanager.utils.exceptions.BookNotFoundException;


@RestController
public class BookController {

    private static final String BASE_PATH = "/books";
    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }


    @GetMapping(BASE_PATH)
    List<Book> all() {
        return repository.findAll();
    }

    @PostMapping(BASE_PATH)
    Book newBook(@RequestBody Book newBook) {
        return repository.save(newBook);
    }


    @GetMapping(BASE_PATH+"/{id}")
    Book one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PutMapping(BASE_PATH+"/{id}")
    Book upsertBook(@RequestBody Book newbook, @PathVariable Long id) {

        return repository.findById(id)
                .map(book -> {
                    book.setName(newbook.getName());
                    book.setEanCode(newbook.getEanCode());
                    book.setAuthor(newbook.getAuthor());
                    book.setPages(newbook.getPages());
                    book.setPublicationDate(newbook.getPublicationDate());
                    return repository.save(book);
                })
                .orElseThrow(() -> {
                    throw new BookNotFoundException(id);
                });
    }

    @DeleteMapping(BASE_PATH+"/{id}")
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }


}