package com.jpashop.domain.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
