package com.apifinancas.controller;

import com.apifinancas.model.CartaoCredito;
import com.apifinancas.service.CartaoCreditoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartoes-credito")
@Tag(name = "Cartões de Crédito", description = "Endpoints para gerenciamento de cartões de crédito")
public class CartaoCreditoController {

    @Autowired
    private CartaoCreditoService cartaoCreditoService;

    @GetMapping
    @Operation(summary = "Listar todos os cartões", description = "Retorna uma lista com todos os cartões de crédito cadastrados")
    public ResponseEntity<List<CartaoCredito>> getAllCartoesCredito() {
        List<CartaoCredito> cartoes = cartaoCreditoService.findAll();
        return ResponseEntity.ok(cartoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cartão por ID", description = "Retorna um único cartão baseado no ID fornecido")
    public ResponseEntity<CartaoCredito> getCartaoCreditoById(@PathVariable Long id) {
        return cartaoCreditoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo cartão", description = "Cria um novo cartão de crédito com os dados fornecidos")
    public ResponseEntity<CartaoCredito> createCartaoCredito(@RequestBody CartaoCredito cartaoCredito) {
        CartaoCredito novoCartao = cartaoCreditoService.save(cartaoCredito);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCartao);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cartão", description = "Atualiza os dados de um cartão existente")
    public ResponseEntity<CartaoCredito> updateCartaoCredito(@PathVariable Long id, @RequestBody CartaoCredito cartaoCreditoDetails) {
        return cartaoCreditoService.update(id, cartaoCreditoDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cartão", description = "Remove um cartão do sistema")
    public ResponseEntity<Void> deleteCartaoCredito(@PathVariable Long id) {
        if (cartaoCreditoService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}