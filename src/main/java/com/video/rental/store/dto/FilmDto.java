/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.video.rental.store.domain.FilmType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author spykee
 */
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class FilmDto {

    private Long id;
    private String title;
    private BigDecimal price;
    private FilmType type;
}
