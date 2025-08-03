package com.github.viinicius_muller.inventory_stock_manager.produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByAtivo(boolean ativo);
    List<Produto> findAllByAtivoTrue();
}
