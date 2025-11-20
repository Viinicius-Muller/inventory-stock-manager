package com.github.viinicius_muller.inventory_stock_manager.services;

import com.github.viinicius_muller.inventory_stock_manager.domain.movimentacao.Movimentacao;
import com.github.viinicius_muller.inventory_stock_manager.domain.movimentacao.MovimentacaoRepository;
import com.github.viinicius_muller.inventory_stock_manager.domain.movimentacao.NewMovimentacaoData;
import com.github.viinicius_muller.inventory_stock_manager.domain.movimentacao.Tipo;
import com.github.viinicius_muller.inventory_stock_manager.domain.produto.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {
    @Autowired
    private final MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private final ProdutoRepository produtoRepository;

    @Transactional
    public ResponseEntity createNewMovimentacao(NewMovimentacaoData data, Long produto_id) {
        var produto = produtoRepository.findById(produto_id).
                orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        if (data.tipo() == Tipo.SAIDA) {
            if (produto.getEstoque_atual() < data.quantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para realizar a saída.");
            }

            produto.setEstoque_atual(produto.getEstoque_atual() - data.quantidade());
        } else if (data.tipo() == Tipo.ENTRADA) {
            produto.setEstoque_atual(produto.getEstoque_atual() + data.quantidade());
        }

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setQuantidade(data.quantidade());

        if (data.dataHora() != null) movimentacao.setData(data.dataHora());
        else movimentacao.setData(LocalDateTime.now());

        movimentacao.setTipo(data.tipo());

        movimentacaoRepository.save(movimentacao);

        return ResponseEntity.ok("Movimentação criada com sucesso.");
    }
}
