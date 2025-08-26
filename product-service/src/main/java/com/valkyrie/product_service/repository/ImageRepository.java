package com.valkyrie.product_service.repository;

import com.valkyrie.product_service.model.Image;
import com.valkyrie.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    void deleteAllByProduct(Product product);

    List<Image> findAllByProduct(Product product);
}
