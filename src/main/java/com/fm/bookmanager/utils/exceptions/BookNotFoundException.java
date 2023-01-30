package com.fm.bookmanager.utils.exceptions;

public class BookNotFoundException extends RuntimeException
{
    public BookNotFoundException(Long id)
    {
        super("Book was not found by id "+id);
    }

}
