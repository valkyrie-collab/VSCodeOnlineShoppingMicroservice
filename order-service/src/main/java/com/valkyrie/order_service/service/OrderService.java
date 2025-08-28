package com.valkyrie.order_service.service;

import com.valkyrie.order_service.config.TokenConfig;
import com.valkyrie.order_service.model.Order;
import com.valkyrie.order_service.model.Store;
import com.valkyrie.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    private OrderRepository repo;
    @Autowired
    private void setRepo(OrderRepository repo) {this.repo = repo;}

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    public Store<String> save(String token, String productId, Order order) {
        String username = config.getUsername(token);
        String uuid = UUID.randomUUID().toString();
        order = order.setCustomerId(username).setProductId(productId).setId(uuid);
    }
}
