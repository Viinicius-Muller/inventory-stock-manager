package com.github.viinicius_muller.inventory_stock_manager.produto;

public record NewProdutoData(
        String nome,
        String descricao,
        String categoria,
        int estoque_atual,
        int estoque_minimo) {
}
