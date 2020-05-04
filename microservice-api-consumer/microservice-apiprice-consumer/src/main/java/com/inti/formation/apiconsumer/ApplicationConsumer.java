package com.inti.formation.apiconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author CestVianney
 * @author AlexandreBuc
 */
@SpringBootApplication
@EnableElasticsearchRepositories
public class ApplicationConsumer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConsumer.class, args);
    }
}
