package com.apifinancas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_cartao_credito")
public class CartaoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCartao;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal limite;

    @Column(nullable = false)
    private String bandeira;

    @Column(nullable = false)
    private Integer validadeMes;

    @Column(nullable = false)
    private Integer validadeAno;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;
}