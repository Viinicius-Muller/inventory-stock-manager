package com.github.viinicius_muller.inventory_stock_manager.domain.produto;

import java.math.BigDecimal;

public record ProductListData(Long id, String produto, String descricao, String categoria, int estoque, BigDecimal preco) {
    public ProductListData(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria().getCategoria(),
                produto.getEstoque_atual(),
                produto.getPreco());
    }
}
