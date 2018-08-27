package ru.dobrotrener.spring5webfluxrest.controller.v1;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dobrotrener.spring5webfluxrest.domain.Category;
import ru.dobrotrener.spring5webfluxrest.repository.CategoryRepository;

@Slf4j
@RequestMapping(value = {CategoryController.BASE_URL})
@RestController
public class CategoryController {


    public static final String BASE_URL = "/api/v1/categories/";
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    Flux<Category> getAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("{id}")
    Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

}
