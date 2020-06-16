package com.rafiki.webfluxrest.controllers;

import com.rafiki.webfluxrest.domain.Vendor;
import com.rafiki.webfluxrest.repository.VendorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

    private final VendorRepository repository;

    public VendorController(VendorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("api/v1/vendors")
    Flux<Vendor> list(){
        return repository.findAll();
    }

    @GetMapping("api/v1/vendors/{id}")
    Mono<Vendor> getById(@PathVariable String id){
        return repository.findById(id);
    }


}
