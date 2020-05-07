package com.inti.formation.input.converter;

import com.inti.formation.input.model.PriceInput;
import com.inti.formation.input.mongo.PriceMongo;
import org.springframework.stereotype.Component;

@Component
public class ConverterImpl implements Converter {


    @Override
    public PriceMongo convert(PriceInput p) {

        PriceMongo price = new PriceMongo();
        price.setIdPrix(p.getIdPrix());
        price.setMontant(p.getMontant());
        price.setActive(p.isActive());
        price.setCode(p.getCode());
        price.setDate(p.getDate());
        price.setDateSuppr(price.getDateSuppr());

        return price;
    }
}
