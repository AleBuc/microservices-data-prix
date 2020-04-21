package com.inti.formation.shop.api.service;

import com.inti.formation.shop.api.repository.PriceRepository;
import com.inti.formation.shop.api.repository.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Objects;
/**
 * @author Sylvanius Kouandongui
 */

@Component
@Slf4j
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Override
    public Mono<Price> register(final Price price) {
        return priceRepository.save(price);
    }

    @Override
    public Flux<Price> getPrices() {
        return priceRepository.findAll();
    }

    @Override
    public Mono<Price> update(Price price) {
        return priceRepository.save(price);
    }

    @Override
    public Mono<Price> findByIdPrix(long id) {
        return priceRepository.findByIdPrix(id);
    }

    @Override
    public Flux<Price> findActivatedByDate(Date date) {
        return priceRepository.findActivatedByDate(date);
    }

    @Override
    public Mono<Price> delete(String id) {
        System.out.println(priceRepository.findByIdPrix(Long.parseLong(id)));
        return this.priceRepository.findByIdPrix(Long.parseLong(id)).flatMap(p -> this.priceRepository.delete(p).thenReturn(p));
    }

}
