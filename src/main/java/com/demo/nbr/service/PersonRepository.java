package com.demo.nbr.service;

import com.demo.nbr.entity.Human;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PersonRepository extends CrudRepository<Human,String> {

    @Query("MATCH(b:human)-[r]-(e) return b,collect(r),collect(e)")
    Collection<Human> customQuery();
}
