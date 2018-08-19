/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.domain;

import com.video.rental.store.domain.relationship.RentedFilm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author spykee
 */
@NodeEntity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int bonusPoints;

    @Relationship(type = "RENTED")
    private List<RentedFilm> films = new ArrayList<>();


    public void addBonusPoints(int bonusPoints) {
        this.bonusPoints += bonusPoints;
    }


    public void addRentedFilm(Film film, int rentPeriod, Price rentPrice) {
        RentedFilm rentedFilm = new RentedFilm();

        rentedFilm.setCustomerRentingPrice(rentPrice);
        rentedFilm.setCustomer(this);
        rentedFilm.setFilm(film);
        rentedFilm.setRentPeriod(rentPeriod);
        films.add(rentedFilm);
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", bonusPoints=" + bonusPoints + ", films=" + films + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 19 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }

}
