package com.github.viinicius_muller.inventory_stock_manager.movimentacao;

import java.time.LocalDate;

public record NewMovimentacaoData(
        int produto_id,
        int quantidade,
        LocalDate date,
        String tipo
) {
}
