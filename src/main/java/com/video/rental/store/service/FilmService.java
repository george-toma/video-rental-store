package com.video.rental.store.service;

import com.video.rental.store.api.model.inbound.RentInboundDto;
import com.video.rental.store.api.model.outbound.RentOutboundDto;
import com.video.rental.store.api.model.outbound.RentOutboundFilmDescriptionDto;
import com.video.rental.store.api.model.outbound.RentReturnOutboundDto;
import com.video.rental.store.api.model.outbound.RentReturnOutboundFilmDescriptionDto;
import com.video.rental.store.domain.Customer;
import com.video.rental.store.domain.Film;
import com.video.rental.store.domain.Price;
import com.video.rental.store.dto.FilmDto;
import com.video.rental.store.dto.mapper.BiMapper;
import com.video.rental.store.dto.mapper.Mapper;
import com.video.rental.store.exception.FilmNotFoundException;
import com.video.rental.store.repository.CustomerRepository;
import com.video.rental.store.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("film")
    private Mapper mapper;

    @Autowired
    @Qualifier("rentInboundToOutbound")
    private Mapper inbountToOutboundMapper;

    @Autowired
    @Qualifier("rentInboundToReturnOutbound")
    private BiMapper<RentReturnOutboundFilmDescriptionDto, RentInboundDto.InboundFilmDescriptionDto, String> rentInboundToReturnOutboundMapper;

    public List<FilmDto> doFindFilmsFlow(String[] titles) {
        List<FilmDto> films = new ArrayList<>(titles.length);
        for (String title : titles) {
            films.add(findFilm(title));
        }
        return films;
    }

    private Boolean existsFilm(String title) {
        return Boolean.TRUE.equals(filmRepository.existsByTitle(title));
    }

    public FilmDto findFilm(String title) {
        Film film = getFilmFromGraph(title);
        if (film != null) {
            return (FilmDto) mapper.convertTo(film);
        } else {
            throw new FilmNotFoundException(title);
        }
    }

    @Transactional
    public RentOutboundDto doRentFilmFlow(RentInboundDto inboundDto) {
        Customer customer = createCustomer(inboundDto);
        RentOutboundDto outboundDto = new RentOutboundDto();
        inboundDto.getFilmsToRent().stream()
                .filter((dto) -> {
                            if (existsFilm(dto.getTitle())) {
                                return true;
                            } else {
                                RentOutboundFilmDescriptionDto outFilm = new RentOutboundFilmDescriptionDto();
                                outFilm.setTitle(dto.getTitle());
                                outboundDto.addNotFoundFilm(outFilm);
                                return false;
                            }
                        }
                )
                .map((dto) -> createRentOutbountFilm(outboundDto, dto))
                .forEach((dto) -> {
                    populateCustomer(dto, customer);
                    outboundDto.setTotalPrice(new Price(outboundDto.getTotalPrice().add(dto.getPrice()).toPlainString()));
                });



        final boolean noMovieWasRented = outboundDto.getFilmsToRent().isEmpty();
        if (!noMovieWasRented) {
            saveCustomerInDatabase(customer);
        }

        return outboundDto;
    }

    @Transactional
    public RentReturnOutboundDto doReturnRentedFilmFlow(RentInboundDto inboundDto) {
        RentReturnOutboundDto outboundDto = new RentReturnOutboundDto();
        final Price totalLateCharge = new Price(0.0D);
        inboundDto.getFilmsToRent().stream()
                .filter((dto) -> {
                    setDefaultRentingDate(dto);
                    if (wasFilmRented(inboundDto.getCustomerName(), dto.getTitle(), dto.getRentStartingDate())) {
                        return true;
                    } else {
                        createNotFoundFilmReference(outboundDto, dto);
                        return false;
                    }

                })
                .map(dto -> {
                    return rentInboundToReturnOutboundMapper.convertTo(dto, inboundDto.getCustomerName());
                })
                .forEach((dto) -> {
                    outboundDto.addFilm(dto);
                    outboundDto.setTotalPriceCharge(new Price(outboundDto.getTotalPriceCharge().add(dto.getPrice()).toPlainString()));
                });




        return outboundDto;
    }

    private void createNotFoundFilmReference(RentReturnOutboundDto outboundDto, RentInboundDto.InboundFilmDescriptionDto dto) {
        RentReturnOutboundFilmDescriptionDto outFilm = new RentReturnOutboundFilmDescriptionDto();
        outFilm.setTitle(dto.getTitle());
        outboundDto.addNotFoundFilm(outFilm);
    }

    private void setDefaultRentingDate(RentInboundDto.InboundFilmDescriptionDto dto) {
        LocalDate rentStartingDate = dto.getRentStartingDate();
        if (rentStartingDate == null) {
            dto.setRentStartingDate(LocalDate.now());
        }
    }

    private RentOutboundFilmDescriptionDto createRentOutbountFilm(
            RentOutboundDto outboundDto, RentInboundDto.InboundFilmDescriptionDto dto) {
        RentOutboundFilmDescriptionDto outFilmDto =
                (RentOutboundFilmDescriptionDto) inbountToOutboundMapper.convertTo(dto);
        outboundDto.addFilm(outFilmDto);
        return outFilmDto;
    }

    private Customer createCustomer(RentInboundDto inboundDto) {
        final Customer customer = new Customer();
        customer.setName(inboundDto.getCustomerName());
        return customer;
    }

    private void populateCustomer(
            RentOutboundFilmDescriptionDto dto,
            Customer customer) {
        Film film = getFilmFromGraph(dto.getTitle());

        customer.addBonusPoints(film.getType().getBonus());
        customer.addRentedFilm(film, dto.getRentingPeriod(), dto.getPrice());
    }

    private Film getFilmFromGraph(String title) {
        return filmRepository.findByTitle(title);
    }

    private void saveCustomerInDatabase(final Customer customer) {
        customerRepository.save(customer);
    }


    private Boolean wasFilmRented(String customerName, String filmTitle, LocalDate rentedDate) {
        return Boolean.TRUE.equals(
                customerRepository.wasFilmRented(customerName, rentedDate.toString(), filmTitle));
    }
}
