package com.github.viinicius_muller.inventory_stock_manager.controller;

import com.github.viinicius_muller.inventory_stock_manager.categoria.*;
import com.github.viinicius_muller.inventory_stock_manager.exception.ActiveObjectException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Operation(description = "Adiciona uma nova categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Nova categoria criada"),
            @ApiResponse(responseCode = "400",description = "Nome de categoria já em uso", content = @Content)
    })
    @PostMapping
    @Transactional
    public Categoria addCategoria(@RequestBody NewCategoriaData data) {
        return categoriaRepository.save(new Categoria(null,true, data.nome()));
    }

    @Operation(description = "Retorna categorias cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna as categorias"),
            @ApiResponse(responseCode = "400",description = "Falha na utilização dos parâmetros", content = @Content)
    })
    @GetMapping
    public List<CategoryListData> getCategorias(
            @Parameter(description = "Atributo 'ativo' do produto", example = "true") @RequestParam(name = "ativo",required = false) Boolean ativo)
    {
        //if ativo param is present, return by ativo
        if (ativo != null ) return categoriaRepository.findByAtivo(ativo).stream().map(CategoryListData::new).toList();
        return categoriaRepository.findAllByAtivoTrue().stream().map(CategoryListData::new).toList();
    }

    @Operation(description = "Atualiza o nome de uma categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Categoria renomeada"),
            @ApiResponse(responseCode = "400",description = "Nome vazio", content = @Content)
    })
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

    @Operation(description = "Altera o atributo Ativo de uma categoria para verdadeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ativa a categoria", content = @Content),
            @ApiResponse(responseCode = "400",description = "Categoria já ativa ou Id inexistente", content = @Content)
    })
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

    @Operation(description = "Altera o atributo Ativo de um produto para falso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Desativa o produto"),
            @ApiResponse(responseCode = "400",description = "Produto já inativo ou Id inexistente")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteCategoria(@PathVariable Long id) {
        var categoriaOpt = categoriaRepository.findById(id);

        if (categoriaOpt.isEmpty()) throw new EntityNotFoundException("Categoria não encontrada.");

        Categoria categoria = categoriaOpt.get();
        categoria.desativar();
    }
}
