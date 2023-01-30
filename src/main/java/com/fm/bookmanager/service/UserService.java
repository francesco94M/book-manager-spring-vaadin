package com.fm.bookmanager.service;

import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fm.bookmanager.entity.User;
import com.fm.bookmanager.repository.implementations.UserRepository;


@Service
public class UserService extends AbstractCrudService<User,Long,UserRepository>
{

    private final UserRepository repository;


    public UserService(UserRepository repository)
    {
        super(repository);
        this.repository = repository;
    }


    public Optional<User> findByUsername(String username) {
        return repository.findAll((Specification<User>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"),username)).stream().findAny();
    }

}
