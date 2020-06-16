package com.rafiki.webfluxrest.controllers;

import com.rafiki.webfluxrest.domain.Category;
import com.rafiki.webfluxrest.repository.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {

    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("api/v1/categories")
    Flux<Category> list(){
        return repository.findAll();
    }

    @GetMapping("api/v1/categories/{id}")
    Mono<Category> getById(@PathVariable String id){
        return repository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/v1/categories")
    Mono<Void> create(Publisher<Category> categoryStream){
        return repository.saveAll(categoryStream).then();
    }

    @PutMapping("api/v1/categories/{id}")
    Mono<Category> update(@PathVariable String id,@RequestBody Category category){
        category.setId(id);
        return repository.save(category);
    }

}
