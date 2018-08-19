package com.video.rental.store.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class Price extends BigDecimal {

    public static final MathContext MATH_CONTEXT = new MathContext(4, RoundingMode.HALF_UP);

    public Price(double val) {
        super(val, MATH_CONTEXT);
    }

    public Price(int val) {
        super(val, MATH_CONTEXT);
    }

    public Price(String val) {
        super(val, MATH_CONTEXT);
    }

}
