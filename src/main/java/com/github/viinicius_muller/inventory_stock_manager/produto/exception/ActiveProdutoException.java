package com.github.viinicius_muller.inventory_stock_manager.produto.exception;

//used for when trying to reactivate a product that is already active
public class ActiveProdutoException extends RuntimeException {
    public ActiveProdutoException(String message) {
        super(message);
    }
}
