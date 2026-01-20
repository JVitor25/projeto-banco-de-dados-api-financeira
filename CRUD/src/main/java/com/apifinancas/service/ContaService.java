package com.apifinancas.service;

import com.apifinancas.model.Conta;
import com.apifinancas.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }

    public Conta save(Conta conta) {
        conta.setDataCriacao(LocalDateTime.now());
        conta.setDataAtualizacao(LocalDateTime.now());
        return contaRepository.save(conta);
    }

    public Optional<Conta> update(Long id, Conta contaDetails) {
        return contaRepository.findById(id).map(conta -> {
            conta.setTipo(contaDetails.getTipo());
            conta.setSaldoInicial(contaDetails.getSaldoInicial());
            conta.setUsuario(contaDetails.getUsuario());
            conta.setGrupo(contaDetails.getGrupo());
            conta.setDataAtualizacao(LocalDateTime.now());
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