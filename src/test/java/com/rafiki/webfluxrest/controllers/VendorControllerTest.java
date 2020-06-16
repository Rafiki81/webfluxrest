package com.rafiki.webfluxrest.controllers;

import com.rafiki.webfluxrest.domain.Category;
import com.rafiki.webfluxrest.domain.Vendor;
import com.rafiki.webfluxrest.repository.VendorRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @Before
    public void setup() throws Exception{
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }


    @Test
    void list() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Matt").lastName("Hastings").build(),
                        Vendor.builder().firstName("Jack").lastName("Tooms").build()));
        webTestClient.get()
                .uri("api/v1/vendors/")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        BDDMockito.given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(Vendor.builder().firstName("Jack").lastName("Reeves").build()));
        webTestClient.get()
                .uri("api/v1/vendors/someId")
                .exchange()
                .expectBodyList(Category.class);
    }
}