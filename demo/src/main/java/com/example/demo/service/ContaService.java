package com.example.demo.service;

import com.example.demo.model.Conta;
import com.example.demo.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }

    public Conta save(Conta conta) {
        return contaRepository.save(conta);
    }

    public Optional<Conta> update(Long id, Conta contaDetails) {
        return contaRepository.findById(id).map(conta -> {
            conta.setTipo(contaDetails.getTipo());
            conta.setSaldoInicial(contaDetails.getSaldoInicial());
            conta.setDataCriacao(contaDetails.getDataCriacao());
            conta.setIdUsuario(contaDetails.getIdUsuario());
            conta.setIdGrupo(contaDetails.getIdGrupo());
            return contaRepository.save(conta);
        });
    }

    public boolean deleteById(Long id) {
        if (contaRepository.existsById(id)) {
            contaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}