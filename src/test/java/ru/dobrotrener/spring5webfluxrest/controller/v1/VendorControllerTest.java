package ru.dobrotrener.spring5webfluxrest.controller.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dobrotrener.spring5webfluxrest.domain.Vendor;
import ru.dobrotrener.spring5webfluxrest.repository.VendorRepository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class VendorControllerTest {

    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String SOME_ID = "VendorId";
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
    public void testGetAll() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(
                        Flux.just(
                                Vendor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build(),
                                Vendor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build()
                        ));
        webTestClient.get()
                .uri(VendorController.BASE_URL)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void testGetById() {
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(
                        Mono.just(
                                Vendor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build()
                        ));
        webTestClient.get()
                .uri(VendorController.BASE_URL + SOME_ID)
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void testCreateMono() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSaveMono = Mono.just(
                Vendor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build());

        webTestClient.post()
                .uri(VendorController.BASE_URL)
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testCreateFlux() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Flux<Vendor> vendorToSaveFlux = Flux.just(
                Vendor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build(),
                Vendor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build()
        );

        webTestClient.post()
                .uri(VendorController.BASE_URL)
                .body(vendorToSaveFlux, Vendor.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testUpdate() {
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdateMono = Mono.just(
                Vendor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build());

        webTestClient.put()
                .uri(VendorController.BASE_URL + SOME_ID)
                .body(vendorToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testPatchWithChanges() {
        Mono<Vendor> originalVendorMono = Mono.just(
                Vendor.builder().firstName(FIRST_NAME).build());

        Mono<Vendor> vendorToUpdateMono = Mono.just(
                Vendor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build());

        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(originalVendorMono);
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(vendorToUpdateMono);



        webTestClient.patch()
                .uri(VendorController.BASE_URL + SOME_ID)
                .body(vendorToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(vendorRepository).save(any());
    }

    @Test
    public void testPatchNoChanges() {
        Mono<Vendor> originalVendorMono = Mono.just(
                Vendor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build());

        Mono<Vendor> vendorToUpdateMono = Mono.just(
                Vendor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build());

        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(originalVendorMono);
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(vendorToUpdateMono);



        webTestClient.patch()
                .uri(VendorController.BASE_URL + SOME_ID)
                .body(vendorToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(vendorRepository, Mockito.never()).save(any());
    }
}