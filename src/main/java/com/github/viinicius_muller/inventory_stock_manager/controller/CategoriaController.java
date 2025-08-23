package com.github.viinicius_muller.inventory_stock_manager.controller;

import com.github.viinicius_muller.inventory_stock_manager.categoria.*;
import com.github.viinicius_muller.inventory_stock_manager.exception.ActiveObjectException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    @Transactional
    public Categoria addCategoria(@RequestBody NewCategoriaData data) {
        return categoriaRepository.save(new Categoria(null,true, data.nome()));
    }

    @GetMapping
    public List<CategoryListData> getCategorias(@RequestParam(name = "ativo",required = false) Boolean ativo) {
        //if ativo param is present, return by ativo
        if (ativo != null ) return categoriaRepository.findByAtivo(ativo).stream().map(CategoryListData::new).toList();
        return categoriaRepository.findAllByAtivoTrue().stream().map(CategoryListData::new).toList();
    }

    @PatchMapping("/{id}")
    @Transactional
    public void updateCategoria(@RequestBody UpdateCategoriaData data, @PathVariable Long id) {
        var categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isEmpty()) throw new EntityNotFoundException("Categoria não encontrada.");
        //if not found, catch exception

        //transforms Optional<Categoria> into Categoria
        Categoria categoria = categoriaOpt.get();
        categoria.update(data);
    }

    @PatchMapping("/{id}/reativar")
    @Transactional
    public @NotBlank String reativarCategoria(@PathVariable Long id) {
        var categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isEmpty()) throw new EntityNotFoundException("Categoria não encontrada.");

        Categoria categoria = categoriaOpt.get();

        if (categoria.isAtivo()) throw new ActiveObjectException("Categoria '"+categoria.getCategoria()+"' já ativa");

        categoria.ativar();
        return "Categoria reativada: " + categoria.getCategoria();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteCategoria(@PathVariable Long id) {
        var categoriaOpt = categoriaRepository.findById(id);

        if (categoriaOpt.isEmpty()) throw new EntityNotFoundException("Categoria não encontrada.");

        Categoria categoria = categoriaOpt.get();
        categoria.desativar();
    }
}
