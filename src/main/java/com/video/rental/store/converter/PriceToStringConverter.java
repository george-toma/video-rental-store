package com.video.rental.store.converter;

import com.video.rental.store.domain.Price;
import org.neo4j.ogm.typeconversion.AttributeConverter;

public class PriceToStringConverter implements AttributeConverter<Price, String> {

    @Override
    public String toGraphProperty(Price value) {
        return value.toPlainString();
    }

    @Override
    public Price toEntityAttribute(String value) {
        return new Price(value);
    }

}