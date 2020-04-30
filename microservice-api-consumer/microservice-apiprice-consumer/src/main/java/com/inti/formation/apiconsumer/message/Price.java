package com.inti.formation.apiconsumer.message;

import lombok.Data;

import java.util.Date;

@Data
public class Price {

    private long idPrix;
    private float montant;
    private boolean active;
    private String code;
    private Date date;

}
