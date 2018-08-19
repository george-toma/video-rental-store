package com.video.rental.store.dto.mapper;

/**
 * Data convertor from type M and N, to type Ts
 */
public interface BiMapper<T, M, N> {

    T convertTo(M m, N n);

}