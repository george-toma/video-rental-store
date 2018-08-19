package com.video.rental.store.api.model.outbound;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.video.rental.store.domain.FilmType;
import com.video.rental.store.domain.Price;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class RentOutboundFilmDescriptionDto {

    protected String title;
    protected FilmType type;
    protected int rentingPeriod;
    protected Price price;
}