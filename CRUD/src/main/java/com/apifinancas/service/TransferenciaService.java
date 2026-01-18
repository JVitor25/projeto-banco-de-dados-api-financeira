package com.apifinancas.service;

import com.apifinancas.model.Transferencia;
import com.apifinancas.repository.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaService {

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    public List<Transferencia> findAll() {
        return transferenciaRepository.findAll();
    }

    public Optional<Transferencia> findById(Long id) {
        return transferenciaRepository.findById(id);
    }

    public Transferencia save(Transferencia transferencia) {
        return transferenciaRepository.save(transferencia);
    }

    public Optional<Transferencia> update(Long id, Transferencia transferenciaDetails) {
        return transferenciaRepository.findById(id).map(transferencia -> {
            transferencia.setValor(transferenciaDetails.getValor());
            transferencia.setDataTransferencia(transferenciaDetails.getDataTransferencia());
            transferencia.setContaOrigem(transferenciaDetails.getContaOrigem());
            transferencia.setContaDestino(transferenciaDetails.getContaDestino());
            transferencia.setLancamentoOrigem(transferenciaDetails.getLancamentoOrigem());
            transferencia.setLancamentoDestino(transferenciaDetails.getLancamentoDestino());
            return transferenciaRepository.save(transferencia);
        });
    }

    public boolean deleteById(Long id) {
        if (transferenciaRepository.existsById(id)) {
            transferenciaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}