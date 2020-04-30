package com.inti.formation.shop.api.repository.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Alexandre Bucamp
 */

@Data


public class KafkaPrice implements Serializable {
    /**
     * price identifier
     */
    @Id
    private long idPrix;
    private float montant;
    private boolean active;
    private String code;
    private String date;
    private String dateSuppr;

}
