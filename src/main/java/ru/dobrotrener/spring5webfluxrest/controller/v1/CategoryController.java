package ru.dobrotrener.spring5webfluxrest.controller.v1;


import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> create(@RequestBody Publisher<Category> categoryStream) {
        return categoryRepository.saveAll(categoryStream)
                .then();
    }

    @PutMapping("{id}")
    Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping("{id}")
    Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {
        Category foundCategory = categoryRepository.findById(id).block();

        if (foundCategory == null) {
            throw new RuntimeException("Category not found for id: " + id);
        }

        if (category.getDescription() != null &&
                !category.getDescription().equals(foundCategory.getDescription())) {
            foundCategory.setDescription(category.getDescription());
            return categoryRepository.save(foundCategory);
        }
        return Mono.just(foundCategory);
    }


}
