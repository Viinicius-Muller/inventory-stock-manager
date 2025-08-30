package com.github.viinicius_muller.inventory_stock_manager.controller;

import com.github.viinicius_muller.inventory_stock_manager.categoria.Categoria;
import com.github.viinicius_muller.inventory_stock_manager.categoria.CategoriaRepository;
import com.github.viinicius_muller.inventory_stock_manager.movimentacao.Movimentacao;
import com.github.viinicius_muller.inventory_stock_manager.movimentacao.MovimentacaoRepository;
import com.github.viinicius_muller.inventory_stock_manager.produto.*;
import com.github.viinicius_muller.inventory_stock_manager.exception.ActiveObjectException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Adiciona um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Novo produto criado"),
            @ApiResponse(responseCode = "400",description = "Falha no corpo da requisição")
    })
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

    @Operation(description = "Retorna produtos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna os produtos"),
            @ApiResponse(responseCode = "400",description = "Falha na utilização dos parâmetros")
    })
    @GetMapping
    public List<ProductListData> getProdutos(
           @Parameter(description = "Atributo 'ativo' do produto", example = "true") @RequestParam(name = "ativo",required = false) Boolean ativo,
           @Parameter(description = "Nome da categoria", example = "Eletronicos") @RequestParam(name = "categoria", required = false) String nomeCategoria)
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
        return produtoRepository.findAllByAtivoTrueAndCategoriaAtivo()
                .stream()
                .map(ProductListData::new)
                .toList();
    }

    @Operation(description = "Retorna um produto pelo seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna o produto"),
            @ApiResponse(responseCode = "400",description = "Id inexistente")
    })
    //get by id
    @GetMapping("/{id}")
    public List<ProductListData> getProdutoById(@PathVariable Long id) {
        var produto = produtoRepository.findById(id);

        if (produto.isEmpty()) throw new EntityNotFoundException("Produto não encontrado.");
        return produto.stream().map(ProductListData::new).toList();
    }

    @Operation(description = "Atualiza atributos de um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Atualiza o produto"),
            @ApiResponse(responseCode = "400",description = "Id inexistente ou falha no corpo da requisição")
    })
    @PatchMapping("/{id}")
    @Transactional
    public void updateProduto(@RequestBody UpdateProdutoData data, @PathVariable Long id) {
       var produto = produtoRepository.getReferenceById(id);
       produto.update(data, movimentacaoRepository);
    }

    @Operation(description = "Altera o atributo Ativo de um produto para verdadeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ativa o produto"),
            @ApiResponse(responseCode = "400",description = "Produto já ativo ou Id inexistente")
    })
    @PatchMapping("/{id}/reativar")
    @Transactional
    public @NotBlank String reativarProduto(@PathVariable Long id) {
        var produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isEmpty()) throw new EntityNotFoundException("Produto não encontrado.");
        //if not found, catch exception

        //transforms Optional<Produto> into Produto
        Produto produto = produtoOpt.get();

        if (produto.isAtivo()) throw new ActiveObjectException("Produto '"+produto.getNome()+"'já ativo");

        produto.ativar();
        return "Produto reativado: " + produto.getNome();
    }

    @Operation(description = "Altera o atributo Ativo de um produto para falso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Desativa o produto"),
            @ApiResponse(responseCode = "400",description = "Produto já inativo ou Id inexistente")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteProduto(@PathVariable Long id) {
        var produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isEmpty()) throw new EntityNotFoundException("Produto não encontrado.");

        Produto produto = produtoOpt.get();
        produto.desativar();
    }
}
