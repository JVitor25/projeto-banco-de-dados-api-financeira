package com.apifinancas.service;

import com.apifinancas.exception.ValidationException;
import com.apifinancas.model.Fatura;
import com.apifinancas.repository.FaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FaturaService {

    @Autowired
    private FaturaRepository faturaRepository;

    public List<Fatura> findAll() {
        return faturaRepository.findAll();
    }

    public Optional<Fatura> findById(Long id) {
        return faturaRepository.findById(id);
    }

    public Fatura save(Fatura fatura) {
        Long idCartao = fatura.getCartaoCredito().getIdCartao();
        List<Fatura> listaFatura = faturaRepository.findByMesAnoAndCartao(
                fatura.getMesReferencia(),
                fatura.getAnoReferencia(),
                idCartao
        );
        if (!listaFatura.isEmpty())
            throw new ValidationException(
                    String.format("Já existe uma fatura para este cartão no período %s/%s.",
                            fatura.getMesReferencia(),
                            fatura.getAnoReferencia()
                    ));
        return faturaRepository.save(fatura);
    }

    public Optional<Fatura> update(Long id, Fatura faturaDetails) {
        return faturaRepository.findById(id).map(fatura -> {
            fatura.setMesReferencia(faturaDetails.getMesReferencia());
            fatura.setAnoReferencia(faturaDetails.getAnoReferencia());
            fatura.setValorTotal(faturaDetails.getValorTotal());
            fatura.setStatusPagamento(faturaDetails.getStatusPagamento());
            fatura.setDataFechamento(faturaDetails.getDataFechamento());
            fatura.setDataVencimento(faturaDetails.getDataVencimento());
            fatura.setCartaoCredito(faturaDetails.getCartaoCredito());
            return faturaRepository.save(fatura);
        });
    }

    public boolean deleteById(Long id) {
        if (faturaRepository.existsById(id)) {
            faturaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}