package com.fm.bookmanager.repository.implementations;

import org.springframework.stereotype.Repository;

import com.fm.bookmanager.entity.User;
import com.fm.bookmanager.repository.AbstractRepository;


@Repository
public interface UserRepository extends AbstractRepository<User, Long>
{

}
