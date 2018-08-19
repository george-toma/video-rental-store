/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.domain;

import com.video.rental.store.converter.PriceToStringConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.Objects;

/**
 * @author spykee
 */
@Getter
@Setter
@NodeEntity
@NoArgsConstructor
public class Film {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Convert(PriceToStringConverter.class)
    private Price price;
    private FilmType type;

    @Override
    public String toString() {
        return "Film{" + "id=" + id + ", title=" + title + ", price=" + price + ", type=" + type + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.title);
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
        final Film other = (Film) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

}
