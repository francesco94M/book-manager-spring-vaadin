package com.fm.bookmanager.utils.exceptions;

public class EntityNotFoundException extends RuntimeException
{
    public EntityNotFoundException(String entityName,Long id)
    {
        super(entityName+" was not found by id "+id);
    }

}
