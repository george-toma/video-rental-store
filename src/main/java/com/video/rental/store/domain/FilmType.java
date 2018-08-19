/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.domain;

public enum FilmType {
    NEW_RELEASE(1, 2),
    REGULAR_FILM(3, 1),
    OLD_FILM(5, 1);
    private final int days;
    private final int bonus;

    FilmType(int days, int bonus) {
        this.days = days;
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public int getDays() {
        return days;
    }

}
