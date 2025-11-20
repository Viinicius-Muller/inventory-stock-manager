package com.github.viinicius_muller.inventory_stock_manager.domain.produto;

import java.math.BigDecimal;

public record NewProdutoData(
        String nome,
        String descricao,
        Long categoria_id,
        BigDecimal preco,
        int estoque_atual,
        int estoque_minimo,
        Boolean ativo
        ) {
}
