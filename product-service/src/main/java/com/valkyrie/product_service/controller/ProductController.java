package com.valkyrie.product_service.controller;

import java.io.IOException;
import java.util.List;

import com.valkyrie.product_service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valkyrie.product_service.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService service;
    @Autowired
    private void setService(ProductService service) {this.service = service;}

    @PostMapping("/save-product")
    public ResponseEntity<String> save(@RequestParam String token, 
                                        @RequestPart List<MultipartFile> imageFiles,
                                        @RequestParam String productJsonString) throws IOException {
        Product product = new ObjectMapper().readValue(productJsonString, Product.class);

        List<Image> images = imageFiles.stream().map(
            image -> {
                
                try {
                    return new Image().setData(image.getBytes())
                        .setName(image.getOriginalFilename())
                        .setType(image.getContentType());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        ).toList();
        images = images.stream().map(image -> image.setProduct(product)).toList();
        Store<String> store = service.save(token, product.setImages(images));

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @PostMapping("/update-product")
    public ResponseEntity<String> update(@RequestParam String token,
                                        @RequestBody Product product) {
        Store<String> store = service.update(token, product);
        
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @PostMapping("/update-product-rating")
    public ResponseEntity<String> updateRating(@RequestParam String id,
                                               @RequestBody Star star,
                                               @RequestParam String token) {
        
        Store<String> store = service.updateRating(id, star, token);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @PostMapping("/update-product-image")
    public ResponseEntity<String> updateImage(@RequestParam String id, 
                                            @RequestPart List<MultipartFile> imageFiles) {
        Store<String> store = service.updateImage(
            id, imageFiles.stream().map(
                image -> { 

                    try {
                    return new Image().setData(image.getBytes())
                                    .setName(image.getOriginalFilename())
                                    .setType(image.getContentType());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            ).toList()
        );
        
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @PostMapping("/update-product-quantity")
    public ResponseEntity<Integer> updateQuantity(@RequestParam String productId,
                                                 @RequestParam int quantity) {
        Store<Integer> store = service.updateQuantity(productId, quantity);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/find-by-search-keyword")
    public ResponseEntity<List<ProductDTO>> findBySearchKeyword(@RequestParam String searchKeyword) {
        Store<List<ProductDTO>> store = service.findBySearchKeyword(searchKeyword);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/find-by-product-id")
    public ResponseEntity<ProductDTO> findByProductId(@RequestParam String id) {
        Store<ProductDTO> store = service.findByProductId(id);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/find-by-seller-id")
    public ResponseEntity<List<ProductDTO>> findBySellerId(@RequestParam String sellerId) {
        Store<List<ProductDTO>> store = service.findBySellerId(sellerId);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @DeleteMapping("/delete-all-seller-id")
    public ResponseEntity<String> deleteAllBySellerId(@RequestParam String sellerId) {
        Store<String> store = service.deleteAllProductBySellerId(sellerId);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @DeleteMapping("/delete-product-id")
    public ResponseEntity<String> deleteProductById(@RequestParam String productId) {
        Store<String> store = service.deleteProductById(productId);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }
}
