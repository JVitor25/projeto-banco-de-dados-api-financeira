package com.apifinancas.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
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

    private Long idUsuario;

    private Long idConta;

    private Long idGrupo;

    @Column(nullable = false)
    private Long idFatura;

    @Column(nullable = false)
    private Long idCartao;
}