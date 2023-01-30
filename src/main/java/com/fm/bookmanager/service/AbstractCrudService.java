package com.fm.bookmanager.service;

import java.util.List;
import java.util.Optional;

import com.fm.bookmanager.repository.AbstractRepository;


public class AbstractCrudService<ENTITY,ID,REPOSITORY extends AbstractRepository<ENTITY,ID>>
{
     private final REPOSITORY repository;

    public AbstractCrudService(REPOSITORY repository)
    {
        this.repository = repository;
    }

    public Optional<ENTITY> get(ID id) {
        return repository.findById(id);
    }

    public ENTITY createOrUpdate(ENTITY entity) {
        return repository.save(entity);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

    public List<ENTITY> list()
    {
        return repository.findAll();
    }

    public int count() {
        return (int) repository.count();
    }
}
