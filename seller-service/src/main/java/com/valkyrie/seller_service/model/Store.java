package com.valkyrie.seller_service.model;

import org.springframework.http.HttpStatus;

public class Store<I> {
    private final I instance;
    private final HttpStatus status;

    private Store(I instance, HttpStatus status) {
        this.instance = instance;
        this.status = status;
    }

    public static <I> Store<I> initialize(HttpStatus status, I instance) {
        return new Store<>(instance, status);
    }

    public I getInstance() {return instance;}

    public HttpStatus getStatus() {return status;}
}
