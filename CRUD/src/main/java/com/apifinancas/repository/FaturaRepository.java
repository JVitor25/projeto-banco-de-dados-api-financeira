package com.apifinancas.repository;

import com.apifinancas.model.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {

    @Query("SELECT f FROM Fatura f WHERE f.mesReferencia = :mes AND f.anoReferencia = :ano AND f.cartaoCredito.idCartao = :idCartao")
    List<Fatura> findByMesAnoAndCartao(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("idCartao") Long idCartao);
}