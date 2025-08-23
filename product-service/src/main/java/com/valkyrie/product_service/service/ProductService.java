package com.valkyrie.product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valkyrie.product_service.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository repo;
    @Autowired
    private void setRepo(ProductRepository repo) {this.repo = repo;}

    
}
