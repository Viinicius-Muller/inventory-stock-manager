package com.github.viinicius_muller.inventory_stock_manager.categoria;

public record CategoryListData(Long id, String categoria) {
    public CategoryListData(Categoria categoria) {
        this(categoria.getId(), categoria.getCategoria());
    }
}
