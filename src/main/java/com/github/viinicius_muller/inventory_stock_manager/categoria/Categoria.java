package com.github.viinicius_muller.inventory_stock_manager.categoria;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "categorias")
@Entity(name = "categoria")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean ativo;
    @NotBlank(message = "Nome da categoria não pode estar vazio")
    private String categoria;

    public boolean isAtivo() {
        return this.ativo;
    }

    public void update(UpdateCategoriaData data) {
        if (data.nome() != null && !data.nome().trim().isEmpty()) categoria = data.nome();
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
