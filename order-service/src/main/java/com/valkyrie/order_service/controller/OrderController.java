package com.valkyrie.order_service.controller;

import com.valkyrie.order_service.model.Order;
import com.valkyrie.order_service.model.OrderDTO;
import com.valkyrie.order_service.model.Store;
import com.valkyrie.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService service;
    @Autowired
    private void setService(OrderService service) {this.service = service;}

    @PostMapping("/save-order")
    public ResponseEntity<String> save(@RequestParam String token,
                                       @RequestParam String productId,
                                       @RequestBody Order order) {
        Store<String> store = service.save(token, productId, order);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/find-order")
    public ResponseEntity<List<OrderDTO>> findOrder(@RequestParam String token) {
        Store<List<OrderDTO>> store = service.findOrderDetails(token);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @DeleteMapping("/cancel-order")
    public ResponseEntity<String> cancel(@RequestParam String id) {
        Store<String> store = service.cancel(id);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }
}
