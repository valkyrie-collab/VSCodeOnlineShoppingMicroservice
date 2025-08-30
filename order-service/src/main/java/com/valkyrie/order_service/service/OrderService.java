package com.valkyrie.order_service.service;

import com.valkyrie.order_service.config.TokenConfig;
import com.valkyrie.order_service.feign.ProductFeignController;
import com.valkyrie.order_service.model.Order;
import com.valkyrie.order_service.model.OrderDTO;
import com.valkyrie.order_service.model.ProductDTO;
import com.valkyrie.order_service.model.Store;
import com.valkyrie.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private OrderRepository repo;
    @Autowired
    private void setRepo(OrderRepository repo) {this.repo = repo;}

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    private ProductFeignController feign;
    @Autowired
    private void setFeign(ProductFeignController feign) {this.feign = feign;}

    private OrderDTO getOrder(Order order) {
        ResponseEntity<ProductDTO> response = feign.findByProductId(order.getProductId());

        if (response == null || !response.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return null;
        }

        return new OrderDTO().setCustomerId(order.getCustomerId())
                .setPrice(order.getPrice()).setQuantity(order.getQuantity())
                .setShippingInformation(order.getShippingInformation())
                .setProduct(response.getBody()).setId(order.getId());
    }

    @Transactional
    public Store<String> save(String token, String productId, Order order) {
        String username = config.getUsername(token);
        String uuid = UUID.randomUUID().toString();
        ResponseEntity<Integer> response = feign.updateQuantity(productId, order.getQuantity());

        if (response == null || !response.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "There is no such Product: ");
        }

        if (response.getBody() == null) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "Empty response Body");
        }

        order = order.setCustomerId(username).setId(uuid)
                .setProductId(productId).setQuantity(response.getBody());
        repo.save(order);

        return Store.initialize(HttpStatus.ACCEPTED, "Order saved successfully....");
    }

    @Transactional
    public Store<List<OrderDTO>> findOrderDetails(String token) {
        String username = config.getUsername(token);
        List<Order> orders = repo.findAllByCustomerId(username);
        List<OrderDTO> orderDTOs = orders.stream().map(this::getOrder).toList();

        return Store.initialize(HttpStatus.OK, orderDTOs);
    }

    @Transactional
    public Store<String> cancel(String id) {
        Order order = repo.findById(id).orElse(null);

        if (order == null) {
            return Store.initialize(HttpStatus.OK, "The order is already been canceled");
        }

        ResponseEntity<Integer> response = feign.updateQuantity(
                order.getProductId(), order.getQuantity());

        if (response == null || !response.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "Product is not present..");
        }

        repo.deleteById(id);

        return Store.initialize(HttpStatus.OK, "Delete is successful");
    }
}
