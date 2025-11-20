package com.github.viinicius_muller.inventory_stock_manager.controllers;

import com.github.viinicius_muller.inventory_stock_manager.domain.movimentacao.MovimentacaoListData;
import com.github.viinicius_muller.inventory_stock_manager.domain.movimentacao.MovimentacaoRepository;
import com.github.viinicius_muller.inventory_stock_manager.domain.movimentacao.NewMovimentacaoData;
import com.github.viinicius_muller.inventory_stock_manager.services.MovimentacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    @Autowired
    private final MovimentacaoRepository repository;

    @Autowired
    private final MovimentacaoService service;

    @Operation(description = "Retorna as movimentações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Movimentações retornadas"),
            @ApiResponse(responseCode = "400",description = "Id inexistente", content = @Content)
    })
    //gets movimentacoes by product id
    @GetMapping
    public List<MovimentacaoListData> getMovimentacoes(@Parameter(description = "Retorna todas as movimentações de um produto pelo seu id", example = "1") @RequestParam(name = "produtoId",required = false) Long produto_id) {
        if (produto_id != null) return repository.findAllByProduto_id(produto_id).stream().map(MovimentacaoListData::new).toList();

        return repository.findAll().stream().map(MovimentacaoListData::new).toList();
    }

    @PostMapping("/{produto_id}")
    public ResponseEntity createMovimentacao(@RequestBody @Valid NewMovimentacaoData data, @PathVariable Long produto_id) {
        return service.createNewMovimentacao(data, produto_id);
    }

}
