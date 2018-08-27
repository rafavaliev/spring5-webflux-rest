package ru.dobrotrener.spring5webfluxrest.controller.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dobrotrener.spring5webfluxrest.domain.Category;
import ru.dobrotrener.spring5webfluxrest.repository.CategoryRepository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static reactor.core.publisher.Mono.when;

public class CategoryControllerTest {

    public static final String SOME_CAT = "SomeCat";
    public static final String SOME_ID = "id";
    private WebTestClient webTestClient;
    private CategoryRepository categoryRepository;
    private CategoryController categoryController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void testGetAll() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(
                        Flux.just(
                                Category.builder().description(SOME_CAT).build(),
                                Category.builder().description(SOME_CAT).build()
                        ));
        webTestClient.get()
                .uri(CategoryController.BASE_URL)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void testGetById() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(
                        Mono.just(Category.builder().description(SOME_CAT).build())
                );
        webTestClient.get()
                .uri(CategoryController.BASE_URL + SOME_ID)
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void testCreate() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description(SOME_CAT).build());

        webTestClient.post()
                .uri(CategoryController.BASE_URL)
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdate() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description(SOME_CAT).build());

        webTestClient.put()
                .uri(CategoryController.BASE_URL + SOME_ID)
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus().isOk();
    }
}