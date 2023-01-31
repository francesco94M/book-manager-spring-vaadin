package com.fm.bookmanager.utils.exceptions;

public class BookNotFoundException extends EntityNotFoundException
{
    public BookNotFoundException(Long id)
    {
        super("Book",id);
    }

}
