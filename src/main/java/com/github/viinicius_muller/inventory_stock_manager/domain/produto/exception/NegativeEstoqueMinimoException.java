package com.github.viinicius_muller.inventory_stock_manager.domain.produto.exception;

public class NegativeEstoqueMinimoException extends RuntimeException {
    public NegativeEstoqueMinimoException(String message) {
        super(message);
    }
}
