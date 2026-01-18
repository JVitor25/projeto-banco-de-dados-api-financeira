package com.example.demo.controller;

import com.example.demo.model.Conta;
import com.example.demo.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Conta> getAllContas() {
        return contaService.findAll();
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
    public Conta createConta(@RequestBody Conta conta) {
        return contaService.save(conta);
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