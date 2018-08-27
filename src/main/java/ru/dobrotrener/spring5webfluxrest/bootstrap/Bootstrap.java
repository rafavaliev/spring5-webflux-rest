package ru.dobrotrener.spring5webfluxrest.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.dobrotrener.spring5webfluxrest.domain.Category;
import ru.dobrotrener.spring5webfluxrest.domain.Vendor;
import ru.dobrotrener.spring5webfluxrest.repository.CategoryRepository;
import ru.dobrotrener.spring5webfluxrest.repository.VendorRepository;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;

    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override

    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Loading bootstrap data started");
        createCategories();
        createVendors();
        log.info("Loading bootstrap data ended.");
    }

    private void createVendors() {
        Vendor vendor = new Vendor();
        vendor.setId("1");
        vendor.setFirstName("Rafael");
        vendor.setLastName("Valiev");

        vendorRepository.save(vendor).block();
        log.info("Vendors loaded: " + vendorRepository.findAll().collectList().block().size());
    }

    private void createCategories() {
        Category category = new Category();
        category.setId("1");
        category.setDescription("Desc");

        categoryRepository.save(category).block();
        log.info("Categories loaded: " + categoryRepository.findAll().collectList().block().size());
    }
}
