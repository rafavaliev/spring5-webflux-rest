package ru.dobrotrener.spring5webfluxrest.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dobrotrener.spring5webfluxrest.domain.Vendor;
import ru.dobrotrener.spring5webfluxrest.repository.VendorRepository;

@RestController
@Slf4j
@RequestMapping(value = {VendorController.BASE_URL})
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors/";

    private VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    public Flux<Vendor> getAll() {
        return vendorRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<Vendor> getById(String id) {
        return vendorRepository.findById(id);
    }
}
