package com.fm.bookmanager.utils.exceptions;

public class UserNotFoundException extends EntityNotFoundException
{
    public UserNotFoundException(Long id)
    {
        super("User", id);
    }
}
