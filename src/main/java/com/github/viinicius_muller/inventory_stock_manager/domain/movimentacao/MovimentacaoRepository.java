package com.github.viinicius_muller.inventory_stock_manager.domain.movimentacao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
        List<Movimentacao> findAllByProduto_id(Long produto_id);
}
