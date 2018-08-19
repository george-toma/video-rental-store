/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.api.model.outbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.video.rental.store.domain.Price;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spykee
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class RentOutboundDto {

    protected List<RentOutboundFilmDescriptionDto> filmsToRent = new ArrayList<>();

    protected List<RentOutboundFilmDescriptionDto> notFoundFilms = new ArrayList<>();

    private Price totalPrice = new Price(0.0D);


    public void addFilm(RentOutboundFilmDescriptionDto dto) {
        filmsToRent.add(dto);
    }

    public void addNotFoundFilm(RentOutboundFilmDescriptionDto dto) {
        notFoundFilms.add(dto);
    }

}
