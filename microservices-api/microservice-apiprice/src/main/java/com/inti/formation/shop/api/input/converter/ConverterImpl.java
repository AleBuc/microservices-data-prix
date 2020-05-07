package com.inti.formation.shop.api.input.converter;

import com.inti.formation.shop.api.input.model.PriceInput;
import com.inti.formation.shop.api.repository.model.Price;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ConverterImpl implements Converter {


    @Override
    public Price convert(PriceInput p) {

        Price price = new Price();
        price.setIdPrix(p.getIdPrix());
        price.setMontant(p.getMontant());
        price.setActive(p.isActive());
        price.setCode(p.getCode());
        price.setDate(p.getDate());
        price.setDateSuppr(price.getDateSuppr());

        return price;
    }
}
