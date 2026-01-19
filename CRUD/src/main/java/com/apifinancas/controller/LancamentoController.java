package com.apifinancas.controller;

import com.apifinancas.model.Lancamento;
import com.apifinancas.service.LancamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lancamentos")
@Tag(name = "Lançamentos", description = "Endpoints para gerenciamento de lançamentos financeiros")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @GetMapping
    @Operation(summary = "Listar todos os lançamentos", description = "Retorna uma lista com todos os lançamentos cadastrados")
    public ResponseEntity<List<Lancamento>> getAllLancamentos() {
        List<Lancamento> lancamentos = lancamentoService.findAll();
        return ResponseEntity.ok(lancamentos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar lançamento por ID", description = "Retorna um único lançamento baseado no ID fornecido")
    public ResponseEntity<Lancamento> getLancamentoById(@PathVariable Long id) {
        return lancamentoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo lançamento", description = "Cria um novo lançamento com os dados fornecidos")
    public ResponseEntity<Lancamento> createLancamento(@RequestBody Lancamento lancamento) {
        Lancamento novoLancamento = lancamentoService.save(lancamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLancamento);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar lançamento", description = "Atualiza os dados de um lançamento existente")
    public ResponseEntity<Lancamento> updateLancamento(@PathVariable Long id, @RequestBody Lancamento lancamentoDetails) {
        return lancamentoService.update(id, lancamentoDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar lançamento", description = "Remove um lançamento do sistema")
    public ResponseEntity<Void> deleteLancamento(@PathVariable Long id) {
        if (lancamentoService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}