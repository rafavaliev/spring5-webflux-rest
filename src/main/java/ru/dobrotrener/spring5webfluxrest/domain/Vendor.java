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
public class Vendor {

    @Id
    private String id = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;

    public Vendor() {
    }

    public Vendor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Vendor(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
