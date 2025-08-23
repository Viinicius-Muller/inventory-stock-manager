package com.github.viinicius_muller.inventory_stock_manager.controller;

import com.github.viinicius_muller.inventory_stock_manager.categoria.Categoria;
import com.github.viinicius_muller.inventory_stock_manager.categoria.CategoriaRepository;
import com.github.viinicius_muller.inventory_stock_manager.movimentacao.Movimentacao;
import com.github.viinicius_muller.inventory_stock_manager.movimentacao.MovimentacaoRepository;
import com.github.viinicius_muller.inventory_stock_manager.produto.*;
import com.github.viinicius_muller.inventory_stock_manager.produto.exception.ActiveProdutoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @PostMapping
    @Transactional
    public void addProduto(@RequestBody NewProdutoData data) {
        System.out.println(data.categoria());
        Categoria categoria = categoriaRepository.findByCategoria(data.categoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada: " + data.categoria()));

        Produto p = produtoRepository.save(new Produto(
                null,
                true,
                data.nome(),
                data.descricao(),
                data.preco_atual(),
                data.estoque_atual(),
                data.estoque_minimo(),
                categoria));

        movimentacaoRepository.save(new Movimentacao(
                null,
                p,
                p.getEstoque_atual(),
                LocalDate.now(),
                "Novo produto"
        ));
    }

    @GetMapping
    public List<ProductListData> getProdutos(@RequestParam(name = "ativo",required = false) Boolean ativo) {
        //if ativo param is present, return by ativo
        if (ativo != null) return produtoRepository.findByAtivo(ativo).stream().map(ProductListData::new).toList();
        return produtoRepository.findAllByAtivoTrueAndCategoriaAtivo().stream().map(ProductListData::new).toList();
    }

    @PatchMapping("/{id}")
    @Transactional
    public void updateProduto(@RequestBody UpdateProdutoData data, @PathVariable Long id) {
       var produto = produtoRepository.getReferenceById(id);
       produto.update(data, movimentacaoRepository);
    }

    @PatchMapping("/{id}/reativar")
    @Transactional
    public @NotBlank String reativarProduto(@PathVariable Long id) {
        var produto = produtoRepository.getReferenceById(id);

        if (produto.isAtivo()) {
            throw new ActiveProdutoException("O produto já está ativado: "+ produto);
        }

        produto.ativar();
        return "Produto reativado: " + produto.getNome();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteProduto(@PathVariable Long id) {
        var produto = produtoRepository.getReferenceById(id);
        produto.desativar();
    }
}
