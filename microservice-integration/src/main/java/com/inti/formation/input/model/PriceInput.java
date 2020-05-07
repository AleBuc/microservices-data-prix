package com.inti.formation.input.model;

import lombok.Data;

import java.util.Date;

@Data
public class PriceInput {

    private long idPrix;
    private float montant;
    private boolean active;
    private String code;
    private Date date;

}
