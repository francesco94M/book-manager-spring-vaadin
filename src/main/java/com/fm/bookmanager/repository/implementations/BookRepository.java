package com.fm.bookmanager.repository.implementations;

import org.springframework.stereotype.Repository;

import com.fm.bookmanager.entity.Book;
import com.fm.bookmanager.repository.AbstractRepository;


@Repository
public interface BookRepository extends AbstractRepository<Book, Long>
{

}
