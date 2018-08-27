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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@RequestBody Publisher<Vendor> vendorPublisher) {
        return vendorRepository.saveAll(vendorPublisher).then();
    }

    @PutMapping("{id}")
    public Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("{id}")
    public Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
        Vendor foundVendor = vendorRepository.findById(id).block();
        if (foundVendor == null) {
            throw new RuntimeException("No vendor found for id: " + id);
        }

        if (vendor.getFirstName() != null &&
                !vendor.getFirstName().equals(foundVendor.getFirstName())) {
            foundVendor.setFirstName(vendor.getFirstName());
            return vendorRepository.save(foundVendor);
        }

        if (vendor.getLastName() != null &&
                !vendor.getLastName().equals(foundVendor.getLastName())) {
            foundVendor.setLastName(vendor.getLastName());
            return vendorRepository.save(foundVendor);
        }

        return Mono.just(foundVendor);
    }
}
