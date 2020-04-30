package com.inti.formation.apiconsumer.message;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "learning", type = "price")
@Data
public class Price {

    @Id
    @Field(type = FieldType.Long)
    private long idPrix;
    @Field(type = FieldType.Float)
    private float montant;
    @Field(type = FieldType.Boolean)
    private boolean active;
    @Field(type = FieldType.Keyword)
    private String code;
    @Field(type = FieldType.Date)
    private Date date;

}
