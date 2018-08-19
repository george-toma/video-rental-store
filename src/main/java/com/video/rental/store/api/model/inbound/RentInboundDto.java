/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.api.model.inbound;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

/**
 * @author spykee
 */
@Getter
@Setter
@NoArgsConstructor
public class RentInboundDto {

    @NotNull
    private List<InboundFilmDescriptionDto> filmsToRent;

    @NotNull
    @NotEmpty
    private String customerName;

    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class InboundFilmDescriptionDto {

        @NotNull
        private String title;
        @Positive
        private int rentingPeriod;
        private LocalDate rentStartingDate;

    }

}
