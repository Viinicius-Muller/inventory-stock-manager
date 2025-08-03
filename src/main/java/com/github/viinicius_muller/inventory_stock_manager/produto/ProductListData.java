package com.github.viinicius_muller.inventory_stock_manager.produto;

import com.github.viinicius_muller.inventory_stock_manager.categoria.Categoria;
import jakarta.persistence.Column;

public record ProductListData(String produto, String descricao_do_produto, String categoria, int estoque_atual, int estoque_minimo) {
    public ProductListData(Produto produto) {
        this(
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria().getCategoria(), //gets only the category's name
                produto.getEstoque_atual(),
                produto.getEstoque_minimo());
    }
}
