package com.github.viinicius_muller.inventory_stock_manager.produto;

import com.github.viinicius_muller.inventory_stock_manager.categoria.Categoria;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Optional;

public record UpdateProdutoData(
        String nome,
        String descricao,
        Long categoria_id,
        @PositiveOrZero(message = "Preço deve ser 0 ou positivo.")
        BigDecimal preco,
        Boolean ativo
) {
}
