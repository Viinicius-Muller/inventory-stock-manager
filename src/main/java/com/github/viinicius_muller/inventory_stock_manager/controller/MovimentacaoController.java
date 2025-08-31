package com.github.viinicius_muller.inventory_stock_manager.controller;

import com.github.viinicius_muller.inventory_stock_manager.movimentacao.MovimentacaoListData;
import com.github.viinicius_muller.inventory_stock_manager.movimentacao.MovimentacaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Operation(description = "Retorna as movimentações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Movimentações retornadas"),
            @ApiResponse(responseCode = "400",description = "Id inexistente", content = @Content)
    })
    //gets movimentacoes by product id
    @GetMapping
    public List<MovimentacaoListData> getMovimentacoes(@Parameter(description = "Retorna todas as movimentações de um produto pelo seu id", example = "1") @RequestParam(name = "id",required = false) Long produto_id) {
        if (produto_id != null) return movimentacaoRepository.findAllByProduto_id(produto_id).stream().map(MovimentacaoListData::new).toList();

        return movimentacaoRepository.findAll().stream().map(MovimentacaoListData::new).toList();
    }

}
