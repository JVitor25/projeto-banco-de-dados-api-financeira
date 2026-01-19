package com.apifinancas.service;

import com.apifinancas.model.Transferencia;
import com.apifinancas.repository.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        transferencia.setDataTransferencia(LocalDateTime.now());
        return transferenciaRepository.save(transferencia);
    }

    public Optional<Transferencia> update(Long id, Transferencia transferenciaDetails) {
        return transferenciaRepository.findById(id).map(transferencia -> {
            if (transferenciaDetails.getValor() != null) {
                transferencia.setValor(transferenciaDetails.getValor());
            }
            // Data da transferência geralmente não muda, mas se necessário, pode ser atualizada
            if (transferenciaDetails.getDataTransferencia() != null) {
                transferencia.setDataTransferencia(transferenciaDetails.getDataTransferencia());
            }
            if (transferenciaDetails.getContaOrigem() != null) {
                transferencia.setContaOrigem(transferenciaDetails.getContaOrigem());
            }
            if (transferenciaDetails.getContaDestino() != null) {
                transferencia.setContaDestino(transferenciaDetails.getContaDestino());
            }
            if (transferenciaDetails.getLancamentoOrigem() != null) {
                transferencia.setLancamentoOrigem(transferenciaDetails.getLancamentoOrigem());
            }
            if (transferenciaDetails.getLancamentoDestino() != null) {
                transferencia.setLancamentoDestino(transferenciaDetails.getLancamentoDestino());
            }
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