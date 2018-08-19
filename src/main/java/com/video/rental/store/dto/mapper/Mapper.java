/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.dto.mapper;

/**
 * Data convertor from type S to type T
 * @author spykee
 */
public interface Mapper<T, S> {

    T convertTo(S obj);

}
