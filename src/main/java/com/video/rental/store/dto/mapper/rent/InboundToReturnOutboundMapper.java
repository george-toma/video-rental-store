package com.video.rental.store.dto.mapper.rent;

import com.video.rental.store.api.model.inbound.RentInboundDto;
import com.video.rental.store.api.model.outbound.RentReturnOutboundFilmDescriptionDto;
import com.video.rental.store.domain.Film;
import com.video.rental.store.domain.Price;
import com.video.rental.store.domain.relationship.RentedFilm;
import com.video.rental.store.dto.mapper.BiMapper;
import com.video.rental.store.repository.CustomerRepository;
import com.video.rental.store.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component(value = "rentInboundToReturnOutbound")
public class InboundToReturnOutboundMapper implements
        BiMapper<RentReturnOutboundFilmDescriptionDto, RentInboundDto.InboundFilmDescriptionDto, String> {


    public static final int NO_EXTRA_RENT_DAY = 0;
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public RentReturnOutboundFilmDescriptionDto convertTo(
            RentInboundDto.InboundFilmDescriptionDto obj, String customerName) {

        final Film film = filmRepository.findByTitle(obj.getTitle());
        final RentedFilm rentedFilm =
                customerRepository.findCustomerRentedFilm(customerName
                        , obj.getRentStartingDate().toString()
                        , film.getTitle());


        return createMessage(obj, film, rentedFilm);
    }

    private RentReturnOutboundFilmDescriptionDto createMessage(RentInboundDto.InboundFilmDescriptionDto obj, Film film, RentedFilm rentedFilm) {
        final int rentPeriod = rentedFilm.getRentPeriod();
        RentReturnOutboundFilmDescriptionDto outDto = new RentReturnOutboundFilmDescriptionDto();
        outDto.setTitle(obj.getTitle());
        outDto.setType(film.getType());
        outDto.setRentingPeriod(rentPeriod);
        outDto.setExtraRentingPeriod(Period.between(rentedFilm.getRentStartingDate(), LocalDate.now()).getDays());

        setPrice(outDto, rentedFilm);
        return outDto;
    }

    private void setPrice(RentReturnOutboundFilmDescriptionDto outDto, RentedFilm rentedFilm) {
        final int extraRentingDays = outDto.getExtraRentingPeriod();
        Price finalPrice = null;
        if (extraRentingDays != NO_EXTRA_RENT_DAY) {
            final Price extraRentingDaysObj = new Price(extraRentingDays);
            finalPrice = new Price(rentedFilm.getFilm().getPrice().multiply(extraRentingDaysObj).toPlainString());
        }
        outDto.setPrice(finalPrice);
    }
}
