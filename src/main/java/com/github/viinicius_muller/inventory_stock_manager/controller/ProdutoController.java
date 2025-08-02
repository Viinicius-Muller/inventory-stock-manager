package com.github.viinicius_muller.inventory_stock_manager.controller;

import com.github.viinicius_muller.inventory_stock_manager.categoria.Categoria;
import com.github.viinicius_muller.inventory_stock_manager.categoria.CategoriaRepository;
import com.github.viinicius_muller.inventory_stock_manager.produto.NewProdutoData;
import com.github.viinicius_muller.inventory_stock_manager.produto.ProductListData;
import com.github.viinicius_muller.inventory_stock_manager.produto.Produto;
import com.github.viinicius_muller.inventory_stock_manager.produto.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    @Transactional
    public Produto addProduto(@RequestBody NewProdutoData data) {
        System.out.println(data.categoria());
        Categoria categoria = categoriaRepository.findByCategoria(data.categoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada: " + data.categoria()));

        return produtoRepository.save(new Produto(null,data.nome(),data.descricao(),categoria));
    }

    @GetMapping
    public List<ProductListData> getProdutos() {
        return produtoRepository.findAll().stream().map(ProductListData::new).toList();
    }
}
