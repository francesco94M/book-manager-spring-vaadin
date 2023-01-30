package com.fm.bookmanager.repository;

import org.springframework.stereotype.Repository;

import com.fm.bookmanager.AbstractRepository;
import com.fm.bookmanager.entity.User;


@Repository
public interface UserRepository extends AbstractRepository<User, Long>
{

}
