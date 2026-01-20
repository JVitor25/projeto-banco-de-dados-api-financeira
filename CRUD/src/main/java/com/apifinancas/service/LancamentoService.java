package com.apifinancas.service;

import com.apifinancas.model.Conta;
import com.apifinancas.model.Fatura;
import com.apifinancas.model.Lancamento;
import com.apifinancas.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private ContaService contaService;

    @Autowired
    private FaturaService faturaService;

    public List<Lancamento> findAll() {
        return lancamentoRepository.findAll();
    }

    public Optional<Lancamento> findById(Long id) {
        return lancamentoRepository.findById(id);
    }

        public Lancamento save(Lancamento lancamento) {
        // Ajusta o sinal do valor baseado no Tipo (Receita/Despesa)
        if (lancamento.getTipo() != null && lancamento.getValor() != null) {
            lancamento.setValor(lancamento.getTipo().ajustarValor(lancamento.getValor()));
        }
        lancamento.setData(LocalDate.now());

        Optional<Conta> contaOptional = contaService.findById(lancamento.getConta().getIdConta());
        if (contaOptional.isEmpty()) {
            throw new RuntimeException("Conta não encontrada para o ID informado.");
        }
        Conta conta = contaOptional.get();
        lancamento.setSaldoParcial(conta.getSaldoInicial().add(lancamento.getValor()));

        if (lancamento.getFatura() != null){
            Fatura fatura = faturaService.findById(lancamento.getFatura().getIdFatura()).get();
            fatura.setValorTotal(fatura.getValorTotal().add(lancamento.getValor()));
            faturaService.update(fatura.getIdFatura(), fatura);
        }

        conta.setSaldoInicial(lancamento.getSaldoParcial());
        contaService.update(conta.getIdConta(), conta);

        return lancamentoRepository.save(lancamento);
    }

    public Optional<Lancamento> update(Long id, Lancamento lancamentoDetails) {
        return lancamentoRepository.findById(id).map(lancamento -> {
            // Se o tipo ou valor mudaram, recalcula o sinal
            if (lancamentoDetails.getTipo() != null) {
                lancamento.setTipo(lancamentoDetails.getTipo());
            }
            
            // Pega o valor novo se existir, senão usa o antigo
            java.math.BigDecimal valorParaAjustar = (lancamentoDetails.getValor() != null) 
                    ? lancamentoDetails.getValor() 
                    : lancamento.getValor();

            // Aplica o ajuste de sinal baseado no tipo atual
            if (lancamento.getTipo() != null) {
                lancamento.setValor(lancamento.getTipo().ajustarValor(valorParaAjustar));
            }

            lancamento.setData(lancamentoDetails.getData());
            lancamento.setSaldoParcial(lancamentoDetails.getSaldoParcial());
            lancamento.setDescricao(lancamentoDetails.getDescricao());
            lancamento.setConta(lancamentoDetails.getConta());
            lancamento.setFatura(lancamentoDetails.getFatura());
            lancamento.setCartao(lancamentoDetails.getCartao());
            return lancamentoRepository.save(lancamento);
        });
    }

    public boolean deleteById(Long id) {
        if (lancamentoRepository.existsById(id)) {
            lancamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}