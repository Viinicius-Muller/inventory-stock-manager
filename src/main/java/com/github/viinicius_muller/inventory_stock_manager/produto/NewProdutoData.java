package com.github.viinicius_muller.inventory_stock_manager.produto;

import com.github.viinicius_muller.inventory_stock_manager.categoria.Categoria;

import java.math.BigDecimal;

public record NewProdutoData(
        String nome,
        String descricao,
        String categoria_id,
        BigDecimal preco,
        int estoque_atual,
        int estoque_minimo,
        Boolean ativo
        ) {
}
