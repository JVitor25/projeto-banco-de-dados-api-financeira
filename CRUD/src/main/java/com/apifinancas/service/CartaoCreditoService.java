package com.apifinancas.service;

import com.apifinancas.model.CartaoCredito;
import com.apifinancas.model.Fatura;
import com.apifinancas.repository.CartaoCreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartaoCreditoService {

    @Autowired
    private CartaoCreditoRepository cartaoCreditoRepository;

    @Autowired
    private FaturaService faturaService;

    public List<CartaoCredito> findAll() {
        return cartaoCreditoRepository.findAll();
    }

    public Optional<CartaoCredito> findById(Long id) {
        return cartaoCreditoRepository.findById(id);
    }

    public CartaoCredito save(CartaoCredito cartaoCredito) {
        CartaoCredito savedCartao = cartaoCreditoRepository.save(cartaoCredito);

        // Criar fatura inicial automaticamente usando Builder
        LocalDateTime now = LocalDateTime.now();
        Fatura fatura = Fatura.builder()
                .mesReferencia(now.getMonthValue())
                .anoReferencia(now.getYear())
                .valorTotal(BigDecimal.ZERO)
                .statusPagamento("EM_ABERTO")
                .dataFechamento(now.toLocalDate().plusDays(30))
                .dataVencimento(now.toLocalDate().plusDays(40))
                .cartaoCredito(savedCartao)
                .build();

        faturaService.save(fatura);

        return savedCartao;
    }

    public Optional<CartaoCredito> update(Long id, CartaoCredito cartaoCreditoDetails) {
        return cartaoCreditoRepository.findById(id).map(cartaoCredito -> {
            if (cartaoCreditoDetails.getNome() != null) {
                cartaoCredito.setNome(cartaoCreditoDetails.getNome());
            }
            if (cartaoCreditoDetails.getLimite() != null) {
                cartaoCredito.setLimite(cartaoCreditoDetails.getLimite());
            }
            if (cartaoCreditoDetails.getBandeira() != null) {
                cartaoCredito.setBandeira(cartaoCreditoDetails.getBandeira());
            }
            if (cartaoCreditoDetails.getValidadeMes() != null) {
                cartaoCredito.setValidadeMes(cartaoCreditoDetails.getValidadeMes());
            }
            if (cartaoCreditoDetails.getValidadeAno() != null) {
                cartaoCredito.setValidadeAno(cartaoCreditoDetails.getValidadeAno());
            }
            if (cartaoCreditoDetails.getUsuario() != null) {
                cartaoCredito.setUsuario(cartaoCreditoDetails.getUsuario());
            }
            return cartaoCreditoRepository.save(cartaoCredito);
        });
    }

    public boolean deleteById(Long id) {
        if (cartaoCreditoRepository.existsById(id)) {
            cartaoCreditoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}