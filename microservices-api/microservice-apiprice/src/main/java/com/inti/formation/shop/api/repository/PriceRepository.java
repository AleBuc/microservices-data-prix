package com.inti.formation.shop.api.repository;

import com.inti.formation.shop.api.repository.model.Price;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public interface PriceRepository extends ReactiveMongoRepository<Price, String> {

    /**
     * Retourne les prix actifs après une date donnée
     * @param date    la date
     * @return Prices activated after the Date
     */
    @Query("{'$and':[ {'active': true}, {date': {$gte: ?0} ] }")
    Flux<Price> findActivatedByDate (final Date date);

    @DeleteQuery("{ 'idPrix' : ?0 }")
    Mono<Price> delete (long id);


}