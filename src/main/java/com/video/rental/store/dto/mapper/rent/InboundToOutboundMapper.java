package com.video.rental.store.dto.mapper.rent;

import com.video.rental.store.api.model.inbound.RentInboundDto;
import com.video.rental.store.api.model.outbound.RentOutboundFilmDescriptionDto;
import com.video.rental.store.domain.Film;
import com.video.rental.store.domain.Price;
import com.video.rental.store.dto.mapper.Mapper;
import com.video.rental.store.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "rentInboundToOutbound")
public class InboundToOutboundMapper implements
        Mapper<RentOutboundFilmDescriptionDto, RentInboundDto.InboundFilmDescriptionDto> {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public RentOutboundFilmDescriptionDto convertTo(RentInboundDto.InboundFilmDescriptionDto obj) {
        Film film = filmRepository.findByTitle(obj.getTitle());
        return createMessage(film, obj.getRentingPeriod());
    }

    private RentOutboundFilmDescriptionDto createMessage(Film film, final int rentingPeriod) {
        RentOutboundFilmDescriptionDto filmDto = new RentOutboundFilmDescriptionDto();
        filmDto.setTitle(film.getTitle());
        filmDto.setRentingPeriod(rentingPeriod);
        filmDto.setType(film.getType());
        setPrice(filmDto, film);
        return filmDto;
    }


    private void setPrice(RentOutboundFilmDescriptionDto dto, Film film) {
        final int movieDays = film.getType().getDays();
        final Price filmPrice = new Price(film.getPrice().toPlainString());
        if (movieDays >= dto.getRentingPeriod()) {
            dto.setPrice(filmPrice);
        } else {
            Price multiplicationOrder = new Price(dto.getRentingPeriod() - movieDays);
            final Price extraPrice = new Price(filmPrice.multiply(multiplicationOrder).toPlainString());
            dto.setPrice(new Price(filmPrice.add(extraPrice).toPlainString()));
        }
    }
}
