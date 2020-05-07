package com.inti.formation.input.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author CestVianney
 * @author AlexandreBuc
 */
@Repository
public interface PriceMongoRepository extends MongoRepository<PriceMongo, String> {

}