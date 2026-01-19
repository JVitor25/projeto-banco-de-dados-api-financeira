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

    private String tipo;

    private LocalDate data;

    private BigDecimal saldoParcial;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idConta")
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "idGrupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "idFatura")
    private Fatura fatura;

    @ManyToOne
    @JoinColumn(name = "idCartao")
    private CartaoCredito cartao;
}