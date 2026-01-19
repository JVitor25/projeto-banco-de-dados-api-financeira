package com.apifinancas.service;

import com.apifinancas.model.Lancamento;
import com.apifinancas.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public List<Lancamento> findAll() {
        return lancamentoRepository.findAll();
    }

    public Optional<Lancamento> findById(Long id) {
        return lancamentoRepository.findById(id);
    }

    public Lancamento save(Lancamento lancamento) {
        return lancamentoRepository.save(lancamento);
    }

    public Optional<Lancamento> update(Long id, Lancamento lancamentoDetails) {
        return lancamentoRepository.findById(id).map(lancamento -> {
            if (lancamentoDetails.getValor() != null) {
                lancamento.setValor(lancamentoDetails.getValor());
            }
            if (lancamentoDetails.getTipo() != null) {
                lancamento.setTipo(lancamentoDetails.getTipo());
            }
            if (lancamentoDetails.getData() != null) {
                lancamento.setData(lancamentoDetails.getData());
            }
            if (lancamentoDetails.getSaldoParcial() != null) {
                lancamento.setSaldoParcial(lancamentoDetails.getSaldoParcial());
            }
            if (lancamentoDetails.getDescricao() != null) {
                lancamento.setDescricao(lancamentoDetails.getDescricao());
            }
            if (lancamentoDetails.getUsuario() != null) {
                lancamento.setUsuario(lancamentoDetails.getUsuario());
            }
            if (lancamentoDetails.getConta() != null) {
                lancamento.setConta(lancamentoDetails.getConta());
            }
            if (lancamentoDetails.getGrupo() != null) {
                lancamento.setGrupo(lancamentoDetails.getGrupo());
            }
            if (lancamentoDetails.getFatura() != null) {
                lancamento.setFatura(lancamentoDetails.getFatura());
            }
            if (lancamentoDetails.getCartao() != null) {
                lancamento.setCartao(lancamentoDetails.getCartao());
            }
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