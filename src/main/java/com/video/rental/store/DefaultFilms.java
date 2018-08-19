package com.video.rental.store;

import com.video.rental.store.domain.Film;
import com.video.rental.store.domain.FilmType;
import com.video.rental.store.domain.Price;
import com.video.rental.store.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
public class DefaultFilms {

    @Autowired
    private FilmRepository filmRepository;

    @PostConstruct
    @Transactional
    private void addDefaultFilms() {
        createFilm("Matrix 11", FilmType.NEW_RELEASE, new Price(40.0D));
        createFilm("Spider Man", FilmType.REGULAR_FILM, new Price(30.0D));
        createFilm("Spider Man 2", FilmType.REGULAR_FILM, new Price(30.0D));
        createFilm("Akira", FilmType.OLD_FILM, new Price(30.0D));
        createFilm("Out of Africa", FilmType.OLD_FILM, new Price(30.0D));
        createFilm("La Grande Vadrouille", FilmType.OLD_FILM, new Price(30.0D));
        createFilm("The Troops on Vacation", FilmType.OLD_FILM, new Price(30.0D));

    }


    private void createFilm(String title, FilmType newRelease, Price price) {
        Film film = new Film();
        film.setPrice(price);
        film.setTitle(title);
        film.setType(newRelease);

        filmRepository.save(film);
    }
}
