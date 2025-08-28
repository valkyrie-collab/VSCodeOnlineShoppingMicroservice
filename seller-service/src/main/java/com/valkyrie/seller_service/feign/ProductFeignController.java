package com.valkyrie.seller_service.feign;

import com.valkyrie.seller_service.model.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("PRODUCT-SERVICE")
public interface ProductFeignController {

    @GetMapping("/product/find-by-seller-id")
    ResponseEntity<List<ProductDTO>> findBySellerId(@RequestParam String sellerId);

    @DeleteMapping("/product/delete-all-seller-id")
    ResponseEntity<String> deleteAllBySellerId(@RequestParam String sellerId);
}
