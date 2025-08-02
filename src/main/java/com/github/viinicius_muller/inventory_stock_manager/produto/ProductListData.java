package com.github.viinicius_muller.inventory_stock_manager.produto;

import com.github.viinicius_muller.inventory_stock_manager.categoria.Categoria;
import jakarta.persistence.Column;

public record ProductListData(String produto, String descricao_do_produto, String categoria) {
    public ProductListData(Produto produto) {
        this(
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria().getCategoria()); //gets the name of the Categoria by calling getCategoria inside its JPA
    }
}
