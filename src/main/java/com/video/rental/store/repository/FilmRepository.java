/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.repository;

import com.video.rental.store.domain.Film;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author spykee
 */
public interface FilmRepository extends Neo4jRepository<Film, Long> {

    // derived finder
    Film findByTitle(String title);

    @Query("MATCH (f:Film{title:{0}}) return CASE WHEN f IS NULL THEN false ELSE true END as result")
    Boolean existsByTitle(String title);
}
