package com.fm.bookmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface AbstractRepository<ENTITY,ID> extends JpaRepository<ENTITY, ID>, JpaSpecificationExecutor<ENTITY>
{

}
