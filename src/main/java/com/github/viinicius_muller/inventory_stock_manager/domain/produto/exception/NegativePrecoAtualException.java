package com.github.viinicius_muller.inventory_stock_manager.domain.produto.exception;

public class NegativePrecoAtualException extends RuntimeException {
    public NegativePrecoAtualException(String message) {
        super(message);
    }
}
