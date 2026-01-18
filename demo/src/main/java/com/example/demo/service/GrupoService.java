package com.example.demo.service;

import com.example.demo.model.Grupo;
import com.example.demo.repository.GrupoRepository;
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