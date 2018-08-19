/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.dto.mapper;

import com.video.rental.store.domain.Film;
import com.video.rental.store.dto.FilmDto;
import org.springframework.stereotype.Component;

/**
 * @author spykee
 */
@Component(value = "film")
public class FilmMapper implements Mapper<FilmDto, Film> {

    @Override
    public FilmDto convertTo(Film film) {
        FilmDto filmDto = new FilmDto();
        filmDto.setPrice(film.getPrice());
        filmDto.setType(film.getType());
        filmDto.setTitle(film.getTitle());
        return filmDto;
    }
}
