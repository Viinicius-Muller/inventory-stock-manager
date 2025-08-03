package com.github.viinicius_muller.inventory_stock_manager.produto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public record UpdateProdutoData(
        String nome,
        String descricao,
        Integer estoque_atual,
        Integer estoque_minimo,
        BigDecimal preco_atual
) {
}
