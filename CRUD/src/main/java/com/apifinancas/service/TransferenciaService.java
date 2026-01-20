package com.apifinancas.service;

import com.apifinancas.model.Conta;
import com.apifinancas.model.Lancamento;
import com.apifinancas.model.TipoLancamento;
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

    @Autowired
    private LancamentoService lancamentoService;

    public List<Transferencia> findAll() {
        return transferenciaRepository.findAll();
    }

    public Optional<Transferencia> findById(Long id) {
        return transferenciaRepository.findById(id);
    }

    public Transferencia save(Transferencia transferencia) {
        transferencia.setDataTransferencia(LocalDateTime.now());

        Lancamento lancamentoOrigem = Lancamento.builder()
                .tipo(TipoLancamento.TRANSFERENCIA_SAIDA)
                .valor(transferencia.getValor())
                .descricao(
                        String.format(
                                "Transferencia de Saída enviada para a conta %d",
                                transferencia.getContaDestino().getIdConta()
                        )
                )
                .conta(Conta.builder().idConta(transferencia.getContaOrigem().getIdConta()).build())
                .build();

        Lancamento lancamentoDestino = Lancamento.builder()
                .tipo(TipoLancamento.TRANSFERENCIA_ENTRADA)
                .valor(transferencia.getValor())
                .descricao(
                        String.format(
                                "Transferencia de Entrada recebida da conta %d",
                                transferencia.getContaOrigem().getIdConta()
                        )
                )
                .conta(Conta.builder().idConta(transferencia.getContaDestino().getIdConta()).build())
                .build();

        transferencia.setLancamentoOrigem(lancamentoService.save(lancamentoOrigem));
        transferencia.setLancamentoDestino(lancamentoService.save(lancamentoDestino));
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