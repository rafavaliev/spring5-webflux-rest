package ru.dobrotrener.spring5webfluxrest.controller.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dobrotrener.spring5webfluxrest.domain.Vendor;
import ru.dobrotrener.spring5webfluxrest.repository.VendorRepository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class VendorControllerTest {

    VendorRepository vendorRepository;
    VendorController vendorController;
    WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void getAll() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(
                        Flux.just(
                                Vendor.builder().firstName("Rafael").lastName("Valiev").build(),
                                Vendor.builder().firstName("Qualgul").lastName("Habidulina").build()
                        ));
        webTestClient.get()
                .uri(VendorController.BASE_URL)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(
                        Mono.just(
                                Vendor.builder().firstName("Rafael").lastName("Valiev").build()
                        ));
        webTestClient.get()
                .uri(VendorController.BASE_URL + "VendorId")
                .exchange()
                .expectBody(Vendor.class);
    }
}