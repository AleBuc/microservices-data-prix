package com.inti.formation.shop.api.input.converter;

import com.inti.formation.shop.api.input.model.PriceInput;
import com.inti.formation.shop.api.repository.model.Price;

public interface Converter {

    Price convert(final PriceInput p);

}
