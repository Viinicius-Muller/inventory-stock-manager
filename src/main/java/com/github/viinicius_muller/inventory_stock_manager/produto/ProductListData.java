package com.github.viinicius_muller.inventory_stock_manager.produto;

import com.github.viinicius_muller.inventory_stock_manager.categoria.Categoria;
import jakarta.persistence.Column;

import java.math.BigDecimal;

public record ProductListData(Long id, String produto, String descricao_do_produto, String categoria, int estoque_atual, BigDecimal preco_atual) {
    public ProductListData(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria().getCategoria(), //get category's name
                produto.getDescricao(),
                produto.getEstoque_atual(),
                produto.getPreco_atual());
    }
}
