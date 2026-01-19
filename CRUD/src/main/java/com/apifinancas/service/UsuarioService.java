package com.apifinancas.service;

import com.apifinancas.model.Usuario;
import com.apifinancas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setDataAtualizacao(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> update(Long id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(usuario -> {
            boolean updated = false;
            if (usuarioDetails.getNome() != null) {
                usuario.setNome(usuarioDetails.getNome());
                updated = true;
            }
            if (usuarioDetails.getEmail() != null) {
                usuario.setEmail(usuarioDetails.getEmail());
                updated = true;
            }
            if (usuarioDetails.getSenha() != null) {
                usuario.setSenha(usuarioDetails.getSenha());
                updated = true;
            }
            
            if (updated) {
                usuario.setDataAtualizacao(LocalDateTime.now());
            }
            return usuarioRepository.save(usuario);
        });
    }

    public boolean deleteById(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}