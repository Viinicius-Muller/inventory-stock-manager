package com.github.viinicius_muller.inventory_stock_manager.produto;

import com.github.viinicius_muller.inventory_stock_manager.categoria.Categoria;
import com.github.viinicius_muller.inventory_stock_manager.produto.exception.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "produtos")
@Entity(name = "produto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean ativo;
    @NotBlank
    private String nome;
    private String descricao;
    private BigDecimal preco_atual;
    private int estoque_atual;
    private int estoque_minimo;
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public void update(UpdateProdutoData data) {
        if (data.nome() != null && !data.nome().trim().isEmpty()) nome = data.nome();

        if (data.descricao() != null) descricao = data.descricao();

        if (data.estoque_atual() != null) {
            if (data.estoque_atual() < 0) throw new NegativeEstoqueAtualException("O estoque atual não pode ser um valor negativo.");
            estoque_atual = data.estoque_atual();

        }
        if (data.estoque_minimo() != null) {
            if (data.estoque_minimo() < 0) throw new NegativeEstoqueMinimoException("O estoque mínimo não pode ser um valor negativo.");
            estoque_minimo = data.estoque_minimo();

        }

        if (data.preco_atual() != null) {
            if (data.preco_atual().signum() == -1) throw new NegativePrecoAtualException("O preco atual não pode ser um valor negatívo");
            preco_atual = data.preco_atual();
        }
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
