package com.inti.formation.apiconsumer.repository;

import com.inti.formation.apiconsumer.message.Price;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends ElasticsearchCrudRepository<Price, Long> {

}
