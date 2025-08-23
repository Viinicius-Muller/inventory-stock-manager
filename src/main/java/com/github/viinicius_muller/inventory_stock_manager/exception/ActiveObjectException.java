package com.github.viinicius_muller.inventory_stock_manager.exception;

//used for when trying to reactivate a product that is already active
public class ActiveObjectException extends RuntimeException {
    public ActiveObjectException(String message) {
        super(message);
    }
}
