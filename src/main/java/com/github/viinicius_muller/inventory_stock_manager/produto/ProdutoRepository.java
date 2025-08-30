package com.github.viinicius_muller.inventory_stock_manager.produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByAtivo(boolean ativo);
    List<Produto> findAllByAtivoTrue();

    @Query("SELECT p FROM produto p WHERE p.ativo = true AND p.categoria.ativo = true")
    List<Produto> findAllByAtivoTrueAndCategoriaAtivo();

    @Query("SELECT p FROM produto p WHERE p.categoria.categoria = ?1")
    List<Produto> findAllByCategoria(String nomeCategoria);
}
