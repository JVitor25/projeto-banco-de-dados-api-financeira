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
            boolean updated = false;
            if (contaDetails.getTipo() != null) {
                conta.setTipo(contaDetails.getTipo());
                updated = true;
            }
            if (contaDetails.getSaldoInicial() != null) {
                conta.setSaldoInicial(contaDetails.getSaldoInicial());
                updated = true;
            }
            if (contaDetails.getUsuario() != null) {
                conta.setUsuario(contaDetails.getUsuario());
                updated = true;
            }
            if (contaDetails.getGrupo() != null) {
                conta.setGrupo(contaDetails.getGrupo());
                updated = true;
            }
            
            if (updated) {
                conta.setDataAtualizacao(LocalDateTime.now());
            }
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