package com.github.viinicius_muller.inventory_stock_manager.controller;

import com.github.viinicius_muller.inventory_stock_manager.categoria.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    @Transactional
    public Categoria addCategoria(@RequestBody NewCategoriaData data) {
        return categoriaRepository.save(new Categoria(null, data.nome()));
    }

    @GetMapping
    public List<CategoryListData> getCategorias() {
        return categoriaRepository.findAll().stream().map(CategoryListData::new).toList();
    }

    @PatchMapping("/{id}")
    @Transactional
    public void updateCategoria(@RequestBody UpdateCategoriaData data, @PathVariable Long id) {
        var categoria = categoriaRepository.getReferenceById(id);
        categoria.update(data);
    }
}
