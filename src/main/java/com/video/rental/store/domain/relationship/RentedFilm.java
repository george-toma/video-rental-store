package com.video.rental.store.domain.relationship;

import com.video.rental.store.domain.Customer;
import com.video.rental.store.domain.Film;
import com.video.rental.store.domain.Price;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

import java.time.LocalDate;

@RelationshipEntity(type = "RENTED")
@Getter
@Setter
@NoArgsConstructor
public class RentedFilm {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    Customer customer;

    @EndNode
    Film film;

    private int rentPeriod;
    private LocalDate rentStartingDate = LocalDate.now();
    private Price customerRentingPrice;
}
