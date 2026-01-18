package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tbl_faturas")
public class Fatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFatura;

    @Column(nullable = false)
    private Integer mesReferencia;

    @Column(nullable = false)
    private Integer anoReferencia;

    @Column(nullable = false)
    private LocalDate dataFechamento;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private String statusPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CartaoCredito cartaoCredito;
}