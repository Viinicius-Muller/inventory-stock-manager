package com.github.viinicius_muller.inventory_stock_manager.produto;

import java.math.BigDecimal;

public record NewProdutoData(
        String nome,
        String descricao,
        String categoria,
        BigDecimal preco_atual,
        int estoque_atual,
        int estoque_minimo) {
}
