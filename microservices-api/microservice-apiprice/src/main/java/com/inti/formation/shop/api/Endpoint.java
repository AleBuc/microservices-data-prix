package com.inti.formation.shop.api;


import com.inti.formation.shop.api.repository.model.Price;
import com.inti.formation.shop.api.rest.bean.PriceRequest;
import com.inti.formation.shop.api.rest.exception.InternalServerException;
import com.inti.formation.shop.api.rest.exception.ValidationParameterException;
import com.inti.formation.shop.api.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;



@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/shop")
@Slf4j
@Component
public class Endpoint {

    @Autowired
    PriceService priceService;

    @Autowired
    private KafkaTemplate<String, Price> kafkaTemplate;

    @Value("${kafka.topic-name}")
    private String TOPIC;

    @Value("${kafka.compression-type}")
    private String compressionType;


    @ExceptionHandler(ValidationParameterException.class)
    public Mono<ResponseEntity<String>> handlerValidationParameterException(ValidationParameterException e) {
     return Mono.just(
                badRequest().body("Missing parameter: "+ e.getMessage()));
    }

    @ExceptionHandler(InternalServerException.class)
    public Mono<ResponseEntity<String>> handlerInternalServerException() {
        return Mono.just(status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error server has occurred "));
    }

    @PostMapping(value = "/register" , headers = "Accept=application/json; charset=utf-8")
    @ResponseStatus( value  = HttpStatus.CREATED, reason="Price is registered" )
    public Mono<String> create(@RequestBody PriceRequest price) {
        // Vérification des paramètres
        if( ObjectUtils.anyNotNull(price)  && !ObjectUtils.allNotNull(price.getIdPrix(),price.getMontant(), price.getCode(), price.getDate() )){
            log.error("Validation error: one of attributes is not found");
            return Mono.error(new ValidationParameterException("(Validation error message): one of attributes is not found" ));
        }
        return Mono.just(price)
        .map(data-> priceService.register( data).subscribe().toString());
    }

    @GetMapping
    @RequestMapping(value = "/prices/")
    public Flux<Price> getPrices() {
        log.info("All prices searching");
        return priceService.getPrices()
                // uses of map
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map( data-> data);
    }

    @GetMapping
    @RequestMapping(value = "/prices")
    public Flux<Price> getActivatedPricesByDate(@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date ) {
        log.info("Searching  {} ",date );
        return priceService.findActivatedByDate(date)

                // uses of doNext

                .doOnNext(price -> log.info("Price " + price.getIdPrix()+ " is found"));

    }

    @PutMapping(value = "/update" , headers = "Accept=application/json; charset=utf-8")
    @ResponseStatus( value  = HttpStatus.OK, reason="Price is updated" )
    public Mono<String> update(@RequestBody PriceRequest price) {
        // Vérification des paramètres
        if( ObjectUtils.anyNotNull(price)  && !ObjectUtils.allNotNull(price.getIdPrix(),price.getMontant(), price.getCode(), price.getDate() )){
            log.error("Validation error: one of attributes is not found");
            return Mono.error(new ValidationParameterException("(Validation error message): one of attributes is not found" ));
        }
        return Mono.just(price)
                .map(data-> priceService.update( data).subscribe().toString());
    }

    @DeleteMapping(value="/delete", headers = "Accept=application/json; cherset=utf-8")
    @ResponseStatus(value = HttpStatus.OK, reason = "This price is deleted")
    public void delete(@RequestParam(name = "id") String id) {
        System.out.println("Delete de id "+ id);
        priceService.findByIdPrix(Long.parseLong(id)).subscribe(prix -> {
            priceService.delete(String.valueOf(prix.getIdPrix()));
            ProducerRecord<String, Price> producerRecord = new ProducerRecord<>(TOPIC, Long.toString(prix.getIdPrix()), prix);
            kafkaTemplate.send(producerRecord);
        });
    }

}

