package com.valkyrie.product_service.repository;

import com.valkyrie.product_service.model.Star;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, Integer> {
    Star findByCustomerId(String customerId);
}
