package com.inti.formation.shop.api.service;

import com.inti.formation.shop.api.repository.PriceRepository;
import com.inti.formation.shop.api.repository.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Objects;
/**
 * @author Sylvanius Kouandongui
 */

@Component
@Service("priceserv")
@Slf4j
public class PriceServiceImpl implements PriceService {

    @Autowired
    @Qualifier("pricerepo")
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
    public Mono<Void> delete(String id) {
        return
            priceRepository.deleteById(id);
    }

}
