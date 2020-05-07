package com.inti.formation.input.converter;


import com.inti.formation.input.model.PriceInput;
import com.inti.formation.input.mongo.PriceMongo;

public interface Converter {

    PriceMongo convert(final PriceInput p);

}
