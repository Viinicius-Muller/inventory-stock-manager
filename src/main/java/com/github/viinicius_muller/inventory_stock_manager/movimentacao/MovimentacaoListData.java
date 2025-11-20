package com.github.viinicius_muller.inventory_stock_manager.movimentacao;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MovimentacaoListData(Long produto_id, int quantidade, LocalDateTime data, Tipo tipo) {
    public MovimentacaoListData(Movimentacao movimentacao) {
        this(
                movimentacao.getProduto().getId(),
                movimentacao.getQuantidade(),
                movimentacao.getData(),
                movimentacao.getTipo()
        );
    }
}
