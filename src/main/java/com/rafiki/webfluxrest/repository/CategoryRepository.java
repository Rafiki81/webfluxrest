package com.rafiki.webfluxrest.repository;

import com.rafiki.webfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category,String> {

}
