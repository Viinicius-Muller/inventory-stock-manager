package com.github.viinicius_muller.inventory_stock_manager.domain.movimentacao;

import com.github.viinicius_muller.inventory_stock_manager.domain.produto.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;



@Table(name = "movimentacoes")
@Entity(name = "movimentacao")
@Data
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
