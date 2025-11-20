package com.github.viinicius_muller.inventory_stock_manager.movimentacao;

import com.github.viinicius_muller.inventory_stock_manager.produto.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Table(name = "movimentacoes")
@Entity(name = "movimentacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Movimentacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @NotNull(message = "Quantidade não pode ser nula.")
    private int quantidade;

    @NotNull(message = "Data não pode ser nula.")
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;
}
