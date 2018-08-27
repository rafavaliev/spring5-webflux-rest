package ru.dobrotrener.spring5webfluxrest.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Category {

    @Id

    private String id = UUID.randomUUID().toString();
    private String description;
}
