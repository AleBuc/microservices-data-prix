package com.inti.formation.apiconsumer.repository;

import com.inti.formation.apiconsumer.model.Price;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author CestVianney
 * @author AlexandreBuc
 */
@Repository("espricerepo")
public interface PriceESRepository extends ElasticsearchCrudRepository<Price, Long> {

}
