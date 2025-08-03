package com.github.viinicius_muller.inventory_stock_manager.categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByCategoria(String nome);
    List<Categoria> findByAtivo(boolean ativo);
    List<Categoria> findAllByAtivoTrue();
}
