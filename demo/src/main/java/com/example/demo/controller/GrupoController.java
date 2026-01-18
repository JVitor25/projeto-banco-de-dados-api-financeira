package com.example.demo.controller;

import com.example.demo.model.Grupo;
import com.example.demo.service.GrupoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@Tag(name = "Grupos", description = "Endpoints para gerenciamento de grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    @Operation(summary = "Listar todos os grupos", description = "Retorna uma lista com todos os grupos cadastrados")
    public List<Grupo> getAllGrupos() {
        return grupoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar grupo por ID", description = "Retorna um único grupo baseado no ID fornecido")
    public ResponseEntity<Grupo> getGrupoById(@PathVariable Long id) {
        return grupoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo grupo", description = "Cria um novo grupo com os dados fornecidos")
    public Grupo createGrupo(@RequestBody Grupo grupo) {
        return grupoService.save(grupo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar grupo", description = "Atualiza os dados de um grupo existente")
    public ResponseEntity<Grupo> updateGrupo(@PathVariable Long id, @RequestBody Grupo grupoDetails) {
        return grupoService.update(id, grupoDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar grupo", description = "Remove um grupo do sistema")
    public ResponseEntity<Void> deleteGrupo(@PathVariable Long id) {
        if (grupoService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}