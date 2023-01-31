package com.fm.bookmanager.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fm.bookmanager.entity.User;
import com.fm.bookmanager.repository.implementations.UserRepository;
import com.fm.bookmanager.utils.exceptions.UserNotFoundException;


@RestController
public class UserController
{

    public static final String BASE_PATH = "/users";
    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }


    @GetMapping(BASE_PATH)
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping(BASE_PATH)
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }


    @GetMapping(BASE_PATH+"/{id}")
    User one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping(BASE_PATH+"/{id}")
    User upsertUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                   user.setPassword(newUser.getPassword());
                   user.setUsername(newUser.getUsername());
                    return repository.save(newUser);
                })
                .orElseThrow(() -> {
                    throw new UserNotFoundException(id);
                });
    }

    @DeleteMapping(BASE_PATH+"/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }


}