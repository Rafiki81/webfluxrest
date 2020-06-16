package com.rafiki.webfluxrest.repository;

import com.rafiki.webfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor,String> {
}
