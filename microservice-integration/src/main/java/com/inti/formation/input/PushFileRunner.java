package com.inti.formation.input;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@Configuration
@EnableMongoRepositories
public class PushFileRunner {


    /**
     * start the application
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(PushFileRunner.class, args);

    }

}