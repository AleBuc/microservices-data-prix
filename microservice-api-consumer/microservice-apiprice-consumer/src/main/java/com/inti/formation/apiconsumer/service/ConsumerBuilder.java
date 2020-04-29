package com.inti.formation.apiconsumer.service;

import com.inti.formation.apiconsumer.message.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerBuilder {
    @KafkaListener(topics = "${kafka.topic-name}", groupId = "${kafka.consumer-group-id}")
    public void consume(Price price) {
        log.info("Event readed " + price.toString());
    }
}
