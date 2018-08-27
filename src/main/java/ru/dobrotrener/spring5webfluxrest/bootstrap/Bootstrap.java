package ru.dobrotrener.spring5webfluxrest.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.dobrotrener.spring5webfluxrest.domain.Category;
import ru.dobrotrener.spring5webfluxrest.domain.Vendor;
import ru.dobrotrener.spring5webfluxrest.repository.CategoryRepository;
import ru.dobrotrener.spring5webfluxrest.repository.VendorRepository;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading bootstrap data started");
        if (categoryRepository.count().block() == 0) {
            createCategories();
        }
        if (vendorRepository.count().block() == 0) {
            createVendors();
        }
        log.info("Loading bootstrap data ended.");
    }

    private void createVendors() {
        Vendor vendor = Vendor.builder().firstName("Rafael").lastName("Valiev").build();

        vendorRepository.save(vendor).block();
        log.info("Vendors loaded: " + vendorRepository.findAll().collectList().block().size());
    }

    private void createCategories() {
        categoryRepository.save(
                Category.builder().description("Category1 description").build()
        ).block();
        categoryRepository.save(
                Category.builder().description("Category2 description").build()
        ).block();
        categoryRepository.save(
                Category.builder().description("Category3 description").build()
        ).block();
        categoryRepository.save(
                Category.builder().description("Category4 description").build()
        ).block();
        categoryRepository.save(
                Category.builder().description("Category5 description").build()
        ).block();
        log.info("Categories loaded: " + categoryRepository.findAll().collectList().block().size());
    }
}
