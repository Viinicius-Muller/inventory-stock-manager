package com.github.viinicius_muller.inventory_stock_manager.domain.movimentacao;

import java.time.LocalDateTime;

public record NewMovimentacaoData(
        int quantidade,
        LocalDateTime dataHora,
        Tipo tipo
) {
}
