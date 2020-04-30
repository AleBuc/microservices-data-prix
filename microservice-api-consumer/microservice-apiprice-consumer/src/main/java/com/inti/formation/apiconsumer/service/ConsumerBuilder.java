package com.inti.formation.apiconsumer.service;

import com.inti.formation.apiconsumer.model.Price;
import com.inti.formation.apiconsumer.repository.PriceESRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerBuilder {
    @Autowired
    @Qualifier("espricerepo")
    PriceESRepository esrepo;



    @KafkaListener(topics = "${kafka.topic-name}", groupId = "${kafka.consumer-group-id}")
    public void consume(com.inti.formation.apiconsumer.message.Price price) {
        log.info("Event readed " + price.toString());
        Price esPrice = new Price();
        esPrice.setIdPrix(price.getIdPrix());
        esPrice.setMontant(price.getMontant());
        esPrice.setCode(price.getCode());
        esPrice.setDate(price.getDate());
        esPrice.setActive(price.isActive());

        esrepo.save(esPrice);
    }
}
