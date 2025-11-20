package com.github.viinicius_muller.inventory_stock_manager.domain.produto;

import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateProdutoData(
        String nome,
        String descricao,
        Long categoria_id,
        @PositiveOrZero(message = "Preço deve ser 0 ou positivo.")
        BigDecimal preco,
        Boolean ativo
) {
}
