package com.inti.formation.shop.api.service;

import com.inti.formation.shop.api.repository.model.Price;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface PriceService {

    Mono<Price> register(Price price);

    public Flux<Price> getPrices();

    public Mono<Price> update(Price price);

    public Flux<Price> findActivatedByDate(Date date);

    Mono<Void> delete(String id);

}
