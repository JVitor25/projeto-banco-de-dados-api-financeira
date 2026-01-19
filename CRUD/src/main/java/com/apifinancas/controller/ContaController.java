package com.apifinancas.controller;

import com.apifinancas.model.Conta;
import com.apifinancas.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
@Tag(name = "Contas", description = "Endpoints para gerenciamento de contas bancárias")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping
    @Operation(summary = "Listar todas as contas", description = "Retorna uma lista com todas as contas cadastradas")
    public ResponseEntity<List<Conta>> getAllContas() {
        List<Conta> contas = contaService.findAll();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta por ID", description = "Retorna uma única conta baseada no ID fornecido")
    public ResponseEntity<Conta> getContaById(@PathVariable Long id) {
        return contaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova conta", description = "Cria uma nova conta com os dados fornecidos")
    public ResponseEntity<Conta> createConta(@RequestBody Conta conta) {
        Conta novaConta = contaService.save(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conta", description = "Atualiza os dados de uma conta existente")
    public ResponseEntity<Conta> updateConta(@PathVariable Long id, @RequestBody Conta contaDetails) {
        return contaService.update(id, contaDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar conta", description = "Remove uma conta do sistema")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        if (contaService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}