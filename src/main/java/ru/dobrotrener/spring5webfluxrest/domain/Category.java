package ru.dobrotrener.spring5webfluxrest.domain;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
@Builder
public class Category {
    @Id
    private String id = UUID.randomUUID().toString();
    private String description;

    public Category() {
    }

    public Category(String description) {
        this.description = description;
    }

    public Category(String id, String description) {
        this.id = id;
        this.description = description;
    }
}
