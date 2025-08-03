package com.github.viinicius_muller.inventory_stock_manager.controller;

import com.github.viinicius_muller.inventory_stock_manager.categoria.Categoria;
import com.github.viinicius_muller.inventory_stock_manager.categoria.CategoriaRepository;
import com.github.viinicius_muller.inventory_stock_manager.produto.*;
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

        return produtoRepository.save(new Produto(
                null,
                true,
                data.nome(),
                data.descricao(),
                data.preco_atual(),
                data.estoque_atual(),
                data.estoque_minimo(),
                categoria));
    }

    @GetMapping
    public List<ProductListData> getProdutos(@RequestParam(name = "ativo",required = false) Boolean ativo) {
        //if ativo param is present, return by ativo
        if (ativo != null) return produtoRepository.findByAtivo(ativo).stream().map(ProductListData::new).toList();
        return produtoRepository.findAllByAtivoTrue().stream().map(ProductListData::new).toList();
    }

    @PatchMapping("/{id}")
    @Transactional
    public void updateProduto(@RequestBody UpdateProdutoData data, @PathVariable Long id) {
        var produto = produtoRepository.getReferenceById(id);
        produto.update(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteProduto(@PathVariable Long id) {
        var produto = produtoRepository.getReferenceById(id);
        produto.desativar();
    }
}
