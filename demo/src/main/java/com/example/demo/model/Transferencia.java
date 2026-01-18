package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
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
    @JoinColumn(nullable = false)
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Conta contaDestino;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Lancamento lancamentoOrigem;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Lancamento lancamentoDestino;
}
