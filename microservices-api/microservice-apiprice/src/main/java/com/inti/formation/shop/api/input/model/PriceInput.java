package com.inti.formation.shop.api.input.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Data
public class PriceInput {

    private long idPrix;
    private float montant;
    private boolean active;
    private String code;
    private Date date;
    private Date dateSuppr;

}
