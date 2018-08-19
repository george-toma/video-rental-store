package com.video.rental.store.api.model.outbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class RentReturnOutboundFilmDescriptionDto extends RentOutboundFilmDescriptionDto {

    private int extraRentingPeriod;
}