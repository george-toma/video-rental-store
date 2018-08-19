/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.repository;

import com.video.rental.store.domain.Customer;
import com.video.rental.store.domain.Film;
import com.video.rental.store.domain.relationship.RentedFilm;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @author spykee
 */
public interface CustomerRepository extends Neo4jRepository<Customer, Long> {

    // derived finder
    Customer findByName(String name);

    @Query("MATCH (c:Customer{name:{0}})-[:RENTED]->(f:Film) return f")
    List<Film> findCustomerMovies(String filmName);

    @Query("MATCH (c:Customer{name:{0}})-[rf:RENTED{rentStartingDate:{1}}]->(f:Film{title:{2}}) return CASE WHEN rf IS NULL THEN false ELSE true END as result")
    Boolean wasFilmRented(String customerName, String rentedDate, String filmTitle);

    @Query("MATCH (c:Customer{name:{0}})-[rf:RENTED{rentStartingDate:{1}}]->(f:Film{title:{2}}) return c,rf,f")
    RentedFilm findCustomerRentedFilm(String customerName, String rentedDate, String filmTitle);
}
