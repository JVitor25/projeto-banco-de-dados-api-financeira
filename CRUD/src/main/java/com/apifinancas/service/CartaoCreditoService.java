package com.apifinancas.service;

import com.apifinancas.model.CartaoCredito;
import com.apifinancas.repository.CartaoCreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoCreditoService {

    @Autowired
    private CartaoCreditoRepository cartaoCreditoRepository;

    public List<CartaoCredito> findAll() {
        return cartaoCreditoRepository.findAll();
    }

    public Optional<CartaoCredito> findById(Long id) {
        return cartaoCreditoRepository.findById(id);
    }

    public CartaoCredito save(CartaoCredito cartaoCredito) {
        return cartaoCreditoRepository.save(cartaoCredito);
    }

    public Optional<CartaoCredito> update(Long id, CartaoCredito cartaoCreditoDetails) {
        return cartaoCreditoRepository.findById(id).map(cartaoCredito -> {
            cartaoCredito.setNome(cartaoCreditoDetails.getNome());
            cartaoCredito.setLimite(cartaoCreditoDetails.getLimite());
            cartaoCredito.setBandeira(cartaoCreditoDetails.getBandeira());
            cartaoCredito.setValidadeMes(cartaoCreditoDetails.getValidadeMes());
            cartaoCredito.setValidadeAno(cartaoCreditoDetails.getValidadeAno());
            cartaoCredito.setUsuario(cartaoCreditoDetails.getUsuario());
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