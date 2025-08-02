package com.github.viinicius_muller.inventory_stock_manager.controller;

import com.github.viinicius_muller.inventory_stock_manager.produto.NewProdutoData;
import com.github.viinicius_muller.inventory_stock_manager.produto.Produto;
import com.github.viinicius_muller.inventory_stock_manager.produto.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @PostMapping
    @Transactional
    public void addProduto(NewProdutoData data) {
        System.out.println(data);
        repository.save(new Produto(null, data.nome(),data.descricao(),data.categoria()));
    }
}
