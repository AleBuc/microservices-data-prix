package com.inti.formation.shop.api;

import com.inti.formation.shop.api.repository.model.Price;
import com.inti.formation.shop.api.serde.JsonPOJOSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
@EnableReactiveMongoRepositories // activation de spring mongo reactive
public class ApplicationShop {

    @Value("${kafka.bootstrap-server}")
    private String kafkaBrokerUrl;

    @Value("${kafka.acks}")
    private String acks;

    @Value("${kafka.retries}")
    private String retries;

    @Value("${kafka.buffer-memory}")
    private String bufferMemory;

    @Value("${kafka.batch-size}")
    private String batchSize;

    @Value("${kafka.client-id}")
    private String clientId;

    @Value("${kafka.compression-type}")
    private String compressionType;

    @Value("${kafka.user}")
    private String user;

    @Value("${kafka.password}")
    private String password;

    private static final String SECURITY_PROTOCOL = "security.protocol";
    private static final String SASL_MECHANISM = "sasl.mechanism";
    private static final String SASL_JAAS_CONFIG = "sasl.jaas.config";

    /**
     * start the application
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationShop.class, args);

    }

    // Kafka configuration
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> conf = new HashMap<>();
        //Affectation des variables du fichier application.yml au Producer
        conf.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerUrl);
        //Type du Serializer de la clé
        conf.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //Type du Serializer de la valeur
        conf.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonPOJOSerializer.class.getName());
        // If the request fails, the producer can automatically retry,
        conf.put(ProducerConfig.RETRIES_CONFIG, retries);
        // L'acquittement, nombre de replication de la donnée sur les brockers en respectant le nombre de min.async.replicas
        conf.put(ProducerConfig.ACKS_CONFIG, acks);
        conf.put(StreamsConfig.EXACTLY_ONCE, true); // controls that the message is send one time when ack is all
        conf.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        conf.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        conf.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        conf.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType); // lz4 compression mode unused cpu
        // Authentification
        //conf.put(SECURITY_PROTOCOL, "SASL_PLAINTEXT");
        //conf.put(SASL_MECHANISM, "SCRAM-SHA-512");
        //conf.put(SASL_JAAS_CONFIG, "org.apache.kafka.common.security.scram.ScramLoginModule required username=" + user
        //	+ " password=" + password + ";");

        conf.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
        return conf;
    }

    @Bean
    // <type de clé, type de donnée>
    public ProducerFactory<String, Price> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Price> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }



}
