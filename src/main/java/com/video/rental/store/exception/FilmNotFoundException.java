/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author spykee
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such film")
public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException(String message) {
        super(message);
    }

}
