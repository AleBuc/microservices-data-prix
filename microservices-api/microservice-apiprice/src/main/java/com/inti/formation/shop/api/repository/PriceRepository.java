package com.inti.formation.shop.api.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PriceRepository extends ReactiveMongoRepository<Price, String> {

    @Query("{'$and':[ {'active': true}, {date': {$gte: ?0} ] }")
    Flux<Price> findActivatedByDate (final Date date);

}

