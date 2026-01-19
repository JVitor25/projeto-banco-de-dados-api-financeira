package com.apifinancas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_transferencias")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransferencia;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataTransferencia;

    @ManyToOne
    @JoinColumn(name = "idContaOrigem", nullable = false)
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "idContaDestino", nullable = false)
    private Conta contaDestino;

    @ManyToOne
    @JoinColumn(name = "idLancamentoOrigem", nullable = false)
    private Lancamento lancamentoOrigem;

    @ManyToOne
    @JoinColumn(name = "idLancamentoDestino", nullable = false)
    private Lancamento lancamentoDestino;
}