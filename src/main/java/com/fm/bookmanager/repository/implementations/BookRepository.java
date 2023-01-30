package com.fm.bookmanager.repository;

import org.springframework.stereotype.Repository;

import com.fm.bookmanager.AbstractRepository;
import com.fm.bookmanager.entity.Book;


@Repository
public interface BookRepository extends AbstractRepository<Book, Long>
{

}
