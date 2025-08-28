package com.valkyrie.order_service.feign;

import com.valkyrie.order_service.model.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PRODUCT-SERVICE")
public interface ProductFeignController {

    @GetMapping("/product/find-by-product-id")
    ResponseEntity<ProductDTO> findByProductId(@RequestParam String id);
}
