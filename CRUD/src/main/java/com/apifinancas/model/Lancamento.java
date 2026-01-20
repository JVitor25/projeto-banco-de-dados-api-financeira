package com.apifinancas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_lancamentos")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLancamento;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoLancamento tipo;

    private LocalDate data;

    private BigDecimal saldoParcial;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "idConta")
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "idFatura")
    private Fatura fatura;

    @ManyToOne
    @JoinColumn(name = "idCartao")
    private CartaoCredito cartao;

    @OneToOne
    @JoinColumn(name = "idTransferecnia")
    private Transferencia transferencia;
}