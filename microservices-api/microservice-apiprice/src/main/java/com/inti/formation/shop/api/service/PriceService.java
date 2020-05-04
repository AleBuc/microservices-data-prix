package com.inti.formation.shop.api.service;

import com.inti.formation.shop.api.repository.model.Price;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface PriceService {

    Mono<Price> register(Price price);

    Flux<Price> getPrices();

    Mono<Price> update(Price price);

    Flux<Price> findActivatedByDate(Date date);

    Mono<Price> findByIdPrix(long id);

    Mono<Void> delete(String id);

}
