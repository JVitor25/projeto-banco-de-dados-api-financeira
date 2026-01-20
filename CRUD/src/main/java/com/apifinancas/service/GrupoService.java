package com.apifinancas.service;

import com.apifinancas.model.Grupo;
import com.apifinancas.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public List<Grupo> findAll() {
        return grupoRepository.findAll();
    }

    public Optional<Grupo> findById(Long id) {
        return grupoRepository.findById(id);
    }

    public Grupo save(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public Optional<Grupo> update(Long id, Grupo grupoDetails) {
        return grupoRepository.findById(id).map(grupo -> {
            grupo.setNome(grupoDetails.getNome());
            grupo.setDescricao(grupoDetails.getDescricao());
            grupo.setGrupoPai(grupoDetails.getGrupoPai());
            return grupoRepository.save(grupo);
        });
    }

    public boolean deleteById(Long id) {
        if (grupoRepository.existsById(id)) {
            grupoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}