package com.github.viinicius_muller.inventory_stock_manager.domain.produto;

import com.github.viinicius_muller.inventory_stock_manager.domain.categoria.Categoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;


@Table(name = "produtos")
@Entity(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private boolean ativo;

    @NotBlank(message = "Nome do produto não pode estar vazio.")
    private String nome;

    private String descricao;

    @PositiveOrZero(message = "Preço deve ser 0 ou positivo.")
    private BigDecimal preco;

    private int estoque_atual;
    private int estoque_minimo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public void update(UpdateProdutoData data, Categoria categoria) {
        if (data.nome() != null && !data.nome().trim().isEmpty()) nome = data.nome();

        if (data.descricao() != null) this.descricao = data.descricao();

        if (data.preco() != null) this.preco = data.preco();

        if (data.ativo() != null) this.ativo = data.ativo();

        if (categoria != null) this.categoria = categoria;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
