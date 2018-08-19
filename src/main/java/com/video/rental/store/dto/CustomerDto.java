/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.video.rental.store.domain.Film;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author spykee
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CustomerDto {

    private Long id;
    private String name;
    private int bonusPoints;
    private List<Film> films;


}
