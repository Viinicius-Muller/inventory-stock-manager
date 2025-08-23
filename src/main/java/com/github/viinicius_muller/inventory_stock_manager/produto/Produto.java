package com.github.viinicius_muller.inventory_stock_manager.produto;

import com.github.viinicius_muller.inventory_stock_manager.categoria.Categoria;
import com.github.viinicius_muller.inventory_stock_manager.movimentacao.Movimentacao;
import com.github.viinicius_muller.inventory_stock_manager.movimentacao.MovimentacaoRepository;
import com.github.viinicius_muller.inventory_stock_manager.produto.exception.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;


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
    @NotBlank(message = "Nome do produto não pode estar vazio.")
    private String nome;
    private String descricao;
    @PositiveOrZero(message = "Preço deve ser 0 ou positivo.")
    private BigDecimal preco_atual;
    private int estoque_atual;
    private int estoque_minimo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public void update(UpdateProdutoData data, MovimentacaoRepository movimentacaoRepository) {
        if (data.nome() != null && !data.nome().trim().isEmpty()) nome = data.nome();

        if (data.descricao() != null) descricao = data.descricao();

        if (data.estoque_atual() != null) {
            if (data.estoque_atual() < 0) throw new NegativeEstoqueAtualException("O estoque atual não pode ser um valor negativo.");

            String tipo = estoque_atual > data.estoque_atual() ? "Saída" : "Entrada";
            int quantidade =  data.estoque_atual() - estoque_atual;

            movimentacaoRepository.save(new Movimentacao(
                    null,
                    this,
                    quantidade,
                    LocalDate.now(),
                    tipo
            ));
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
