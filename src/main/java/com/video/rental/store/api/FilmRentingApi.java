package com.video.rental.store.api;

import com.video.rental.store.api.model.inbound.RentInboundDto;
import com.video.rental.store.api.model.outbound.RentOutboundDto;
import com.video.rental.store.api.model.outbound.RentReturnOutboundDto;
import com.video.rental.store.dto.FilmDto;
import com.video.rental.store.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FilmRentingApi {

    @Autowired
    private FilmService filmService;

    @RequestMapping(name = "/store/rent/film", method = RequestMethod.GET)
    public List<FilmDto> findFilms(@RequestParam(value = "titles") String[] titles) {
        return filmService.doFindFilmsFlow(titles);
    }

    @RequestMapping(name = "/store/rent/film", method = RequestMethod.POST)
    public RentOutboundDto rentFilms(@Valid @RequestBody RentInboundDto apiModel) {
        return filmService.doRentFilmFlow(apiModel);
    }

    @RequestMapping(name = "/store/rent/return/film", method = RequestMethod.PUT)
    public RentReturnOutboundDto returnRentedFilms(@Valid @RequestBody RentInboundDto apiModel) {
        return filmService.doReturnRentedFilmFlow(apiModel);
    }
}
