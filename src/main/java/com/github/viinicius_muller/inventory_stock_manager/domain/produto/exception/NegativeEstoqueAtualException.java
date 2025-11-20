package com.github.viinicius_muller.inventory_stock_manager.domain.produto.exception;

public class NegativeEstoqueAtualException extends RuntimeException {
    public NegativeEstoqueAtualException(String message) {
        super(message);
    }
}
