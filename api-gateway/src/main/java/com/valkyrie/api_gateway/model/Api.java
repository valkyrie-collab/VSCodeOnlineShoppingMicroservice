package com.valkyrie.api_gateway.model;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Api {
    private RouterFunction<ServerResponse> post(String name, String finalRoute, String initialRoute) {
        return route(name).POST(finalRoute, http()).before(uri(initialRoute)).build();
    }

    private RouterFunction<ServerResponse> get(String name, String finalRoute, String initialRoute) {
        return route(name).GET(finalRoute, http()).before(uri(initialRoute)).build();
    }

    private RouterFunction<ServerResponse> delete(String name, String finalRoute, String initialRoute) {
        return route(name).DELETE(finalRoute, http()).before(uri(initialRoute)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return post(
                "seller-post", "/seller/**", "http://localhost:8081/"
        ).and(
                get(
                        "seller-get", "/seller/**", "http://localhost:8081/"
                )
        ).and(
                delete(
                        "seller-delete", "/seller/**", "http://localhost:8081/"
                )
        ).and(
                post(
                        "product-post", "/product/**", "http://localhost:8082/"
                )
        ).and(
                get(
                        "product-get", "/product/**", "http://localhost:8082/"
                )
        ).and(
                delete(
                        "product-delete", "/product/**", "http://localhost:8082/"
                )
        ).and(
                post(
                        "order-post", "/order/**", "http://localhost:8083/"
                )
        ).and(
                get(
                        "order-get", "/order/**", "http://localhost:8083/"
                )
        ).and(
                delete(
                        "order-delete", "/order/**", "http://localhost:8083/"
                )
        ).and(
                post(
                        "cart-post", "/cart/**", "http://localhost:8084/"
                )
        ).and(
                get(
                        "cart-get", "/cart/**", "http://localhost:8084/"
                )
        ).and(
                delete(
                        "cart-delete", "/cart/**", "http://localhost:8084/"
                )
        ).and(
                post(
                        "authentication", "/user/**", "http://localhost:8085/"
                )
        );
    }
}
