package com.inti.formation.shop.api.service;

import com.inti.formation.shop.api.repository.PriceRepository;
import com.inti.formation.shop.api.repository.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

import static java.util.stream.Collectors.joining;
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
    public Flux<Price> findActivatedByDate(Date date) {
        return priceRepository.findActivatedByDate(date);
    }

    @Override
    public Mono<Void> delete(String id) {
        return priceRepository.deleteById(id);
    }

}
