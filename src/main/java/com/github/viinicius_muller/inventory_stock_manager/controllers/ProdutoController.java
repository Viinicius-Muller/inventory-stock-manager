package com.github.viinicius_muller.inventory_stock_manager.controllers;

import com.github.viinicius_muller.inventory_stock_manager.domain.categoria.Categoria;
import com.github.viinicius_muller.inventory_stock_manager.domain.categoria.CategoriaRepository;
import com.github.viinicius_muller.inventory_stock_manager.domain.movimentacao.MovimentacaoRepository;
import com.github.viinicius_muller.inventory_stock_manager.domain.produto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Operation(description = "Adiciona um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Novo produto criado"),
            @ApiResponse(responseCode = "400",description = "Falha no corpo da requisição")
    })
    @PostMapping
    @Transactional
    public void addProduto(@RequestBody NewProdutoData data) {
        System.out.println(data.categoria_id());

        var categoria = categoriaRepository.findById(data.categoria_id())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada pelo id: " + data.categoria_id()));

        Produto p = new Produto();
        p.setNome(data.nome());
        p.setDescricao(data.descricao());
        p.setCategoria(categoria);
        p.setPreco(data.preco());
        p.setEstoque_atual(data.estoque_atual());
        p.setEstoque_minimo(data.estoque_minimo());
        p.setAtivo(data.ativo());

        produtoRepository.save(p);
    }

    @Operation(description = "Retorna produtos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna os produtos"),
            @ApiResponse(responseCode = "400",description = "Falha na utilização dos parâmetros", content = @Content)
    })
    @GetMapping
    public List<ProductListData> getProdutos(
           @Parameter(description = "Atributo 'ativo' do produto") @RequestParam(name = "ativo",required = false) Boolean ativo,
           @Parameter(description = "Nome da categoria") @RequestParam(name = "categoria", required = false) String nomeCategoria)
    {
        if (nomeCategoria !=null) {
            //throws exception for non-existent category
            var categoria = categoriaRepository.findByCategoria(nomeCategoria).isEmpty();
            if (categoria) throw new EntityNotFoundException("Categoria não encontrada");

            //returns all products from a category by isAtivo bool
            if (ativo != null) {
                return produtoRepository.findAllByCategoriaAndAtivo(nomeCategoria,ativo)
                        .stream()
                        .map(ProductListData::new).
                        toList();
            }

            //returns all products from a category
            return produtoRepository.findAllByCategoria(nomeCategoria)
                    .stream()
                    .map(ProductListData::new)
                    .toList();
        }

        //only returns products where isAtivo = true
        if (ativo != null) return produtoRepository.findByAtivo(ativo)
                .stream()
                .map(ProductListData::new)
                .toList();

        //only returns where product isAtivo = true and category isAtivo = true
        return produtoRepository.findAll()
                .stream()
                .map(ProductListData::new)
                .toList();
    }

    @Operation(description = "Retorna um produto pelo seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna o produto"),
            @ApiResponse(responseCode = "400",description = "Id inexistente", content = @Content)
    })

    @GetMapping("/{id}")
    public ProductListData getProdutoById(@PathVariable Long id) {
        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        return toDTO(produto);
    }

    public ProductListData toDTO(Produto produto) {
        return new ProductListData(produto);
    }

    @Operation(description = "Atualiza atributos de um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Atualiza o produto"),
            @ApiResponse(responseCode = "400",description = "Id inexistente ou falha no corpo da requisição")
    })
    @PatchMapping("/{id}")
    @Transactional
    public void updateProduto(@RequestBody UpdateProdutoData data, @PathVariable Long id) {
       var produto = produtoRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        Categoria categoria = null;

       if (data.categoria_id() != null) {
           categoria = categoriaRepository.findById(data.categoria_id())
                   .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada."));
       }


       produto.update(data, categoria);
    }

    @Operation(description = "Altera o atributo Ativo de um produto para falso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Desativa o produto"),
            @ApiResponse(responseCode = "400",description = "Produto já inativo ou Id inexistente")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteProduto(@PathVariable Long id) {
        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        produtoRepository.delete(produto);
    }
}
