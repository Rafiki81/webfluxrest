package com.rafiki.webfluxrest.controllers;

import com.rafiki.webfluxrest.domain.Category;
import com.rafiki.webfluxrest.repository.CategoryRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;


class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;


    @Before
    public void setup() throws Exception{
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void list() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build()));
        webTestClient.get()
                .uri("api/v1/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        BDDMockito.given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(Category.builder().description("Cat1").build()));
        webTestClient.get()
                .uri("api/v1/categories/someId")
                .exchange()
                .expectBodyList(Category.class);

    }

    @Test
    public void testCreateCategory(){
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some Cat").build());
        webTestClient.post()
                .uri("/api/v1/categories")
                .body(catToSaveMono,Category.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testUpdate(){
        BDDMockito.given((categoryRepository.save(any(Category.class))))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("Some Cat").build());

        webTestClient.put()
                .uri("/api/v1/categories")
                .body(catToUpdateMono,Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

}