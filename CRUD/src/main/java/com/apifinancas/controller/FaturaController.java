package com.apifinancas.controller;

import com.apifinancas.model.Fatura;
import com.apifinancas.service.FaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faturas")
@Tag(name = "Faturas", description = "Endpoints para gerenciamento de faturas de cartão")
public class FaturaController {

    @Autowired
    private FaturaService faturaService;

    @GetMapping
    @Operation(summary = "Listar todas as faturas", description = "Retorna uma lista com todas as faturas cadastradas")
    public ResponseEntity<List<Fatura>> getAllFaturas() {
        List<Fatura> faturas = faturaService.findAll();
        return ResponseEntity.ok(faturas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar fatura por ID", description = "Retorna uma única fatura baseada no ID fornecido")
    public ResponseEntity<Fatura> getFaturaById(@PathVariable Long id) {
        return faturaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova fatura", description = "Cria uma nova fatura com os dados fornecidos")
    public ResponseEntity<Fatura> createFatura(@RequestBody Fatura fatura) {
        Fatura novaFatura = faturaService.save(fatura);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFatura);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fatura", description = "Atualiza os dados de uma fatura existente")
    public ResponseEntity<Fatura> updateFatura(@PathVariable Long id, @RequestBody Fatura faturaDetails) {
        return faturaService.update(id, faturaDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar fatura", description = "Remove uma fatura do sistema")
    public ResponseEntity<Void> deleteFatura(@PathVariable Long id) {
        if (faturaService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}