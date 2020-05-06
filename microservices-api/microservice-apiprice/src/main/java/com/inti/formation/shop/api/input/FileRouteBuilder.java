package com.inti.formation.shop.api.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inti.formation.shop.api.input.converter.Converter;
import com.inti.formation.shop.api.input.model.PriceInput;
import com.inti.formation.shop.api.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class FileRouteBuilder extends RouteBuilder {

    @Autowired
    private Converter converter;

    @Autowired
    @Qualifier("priceserv")
    PriceService priceService;

    /**
     * Utilisation d'Apache Camel pour enregistrer les données d'un fichier JSON dans MongoDB
     *
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        from("file://{{in.directory}}?include={{in.file.filemask}}&preMove=.tmp/&move=.done/${date:now:yyyyMMdd}/${file:name}&sortBy=file:modified&moveFailed=.error/${date:now:yyyyMMdd}/${file:name}")
                .routeId("process_file")
                .setHeader("uid")
                .constant(UUID.randomUUID().toString())
                .process(exchange -> {
                    log.info("FileUid received "+ exchange.getIn().getHeader("uid", String.class) + " from file " + exchange.getIn().getHeader("CamelFileName") + " will be pushed in MongoDB");
                }).process(exchange -> {
                    final String filename = exchange.getIn().getHeader("CamelFileNameOnly", String.class);
                })
                .from("direct:init_file")
                .routeId("init_file")
                .removeHeaders("CamelFile*", "CamelFileNameOnly")
                .split()
                .jsonpath("$[*]")
                .streaming()
                .to("direct:process_file_processing");

        from("direct:process_file_processing")
                .routeId("process_file_to_mongo")
                .marshal()
                .json(JsonLibrary.Jackson)
                .convertBodyTo(String.class)
                .process(exchange -> {
                    final String body = exchange.getIn().getBody(String.class);
                    PriceInput price = new ObjectMapper().readValue(body, PriceInput.class);
                    priceService.register(converter.convert(price));
                    log.info("<<<<< Prix " + price.toString() + " >>>>>");
                }).end();


    }
}
