package com.github.viinicius_muller.inventory_stock_manager.controller;

import com.github.viinicius_muller.inventory_stock_manager.movimentacao.MovimentacaoListData;
import com.github.viinicius_muller.inventory_stock_manager.movimentacao.MovimentacaoRepository;
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

    //gets movimentacoes by product id
    @GetMapping
    public List<MovimentacaoListData> getMovimentacoes(@RequestParam(name = "id",required = false) Long produto_id) {
        if (produto_id != null) return movimentacaoRepository.findAllByProduto_id(produto_id).stream().map(MovimentacaoListData::new).toList();

        return movimentacaoRepository.findAll().stream().map(MovimentacaoListData::new).toList();
    }

}
