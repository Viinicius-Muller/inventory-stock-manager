package com.github.viinicius_muller.inventory_stock_manager.controllers;

import com.github.viinicius_muller.inventory_stock_manager.domain.categoria.*;
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
        var categoria = categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada."));

        categoria.update(data);
    }

    @Operation(description = "Altera o atributo Ativo de um produto para falso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Desativa o produto"),
            @ApiResponse(responseCode = "400",description = "Produto já inativo ou Id inexistente")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteCategoria(@PathVariable Long id) {
        var categoria = categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada."));
        categoriaRepository.delete(categoria);
    }
}
