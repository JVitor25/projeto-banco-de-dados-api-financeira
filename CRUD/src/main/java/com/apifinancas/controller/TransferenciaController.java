package com.apifinancas.controller;

import com.apifinancas.model.Transferencia;
import com.apifinancas.service.TransferenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transferencias")
@Tag(name = "Transferências", description = "Endpoints para gerenciamento de transferências entre contas")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @GetMapping
    @Operation(summary = "Listar todas as transferências", description = "Retorna uma lista com todas as transferências realizadas")
    public ResponseEntity<List<Transferencia>> getAllTransferencias() {
        List<Transferencia> transferencias = transferenciaService.findAll();
        return ResponseEntity.ok(transferencias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transferência por ID", description = "Retorna uma única transferência baseada no ID fornecido")
    public ResponseEntity<Transferencia> getTransferenciaById(@PathVariable Long id) {
        return transferenciaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova transferência", description = "Cria uma nova transferência com os dados fornecidos")
    public ResponseEntity<Transferencia> createTransferencia(@RequestBody Transferencia transferencia) {
        Transferencia novaTransferencia = transferenciaService.save(transferencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTransferencia);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar transferência", description = "Atualiza os dados de uma transferência existente")
    public ResponseEntity<Transferencia> updateTransferencia(@PathVariable Long id, @RequestBody Transferencia transferenciaDetails) {
        return transferenciaService.update(id, transferenciaDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar transferência", description = "Remove uma transferência do sistema")
    public ResponseEntity<Void> deleteTransferencia(@PathVariable Long id) {
        if (transferenciaService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}