package com.github.viinicius_muller.inventory_stock_manager.produto.exception;

public class NegativeEstoqueAtualException extends RuntimeException {
    public NegativeEstoqueAtualException(String message) {
        super(message);
    }
}
