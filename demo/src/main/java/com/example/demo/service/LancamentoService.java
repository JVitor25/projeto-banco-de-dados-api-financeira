package com.example.demo.service;

import com.example.demo.model.Lancamento;
import com.example.demo.repository.LancamentoRepository;
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
            lancamento.setValor(lancamentoDetails.getValor());
            lancamento.setTipo(lancamentoDetails.getTipo());
            lancamento.setData(lancamentoDetails.getData());
            lancamento.setSaldoParcial(lancamentoDetails.getSaldoParcial());
            lancamento.setDescricao(lancamentoDetails.getDescricao());
            lancamento.setIdUsuario(lancamentoDetails.getIdUsuario());
            lancamento.setIdConta(lancamentoDetails.getIdConta());
            lancamento.setIdGrupo(lancamentoDetails.getIdGrupo());
            lancamento.setIdFatura(lancamentoDetails.getIdFatura());
            lancamento.setIdCartao(lancamentoDetails.getIdCartao());
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