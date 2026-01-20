package com.apifinancas.model;

import com.apifinancas.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;
import java.util.Arrays;

public enum TipoLancamento {
    RECEITA,
    DESPESA,
    TRANSFERENCIA_SAIDA,
    TRANSFERENCIA_ENTRADA;

    @JsonCreator
    public static TipoLancamento fromString(String value) {
        try {
            return TipoLancamento.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException(
                    "Tipo de lançamento inválido: '" + value + "'. Valores aceitos: " + Arrays.toString(TipoLancamento.values())
            );
        }
    }

    public BigDecimal ajustarValor(BigDecimal valor) {
        if (valor == null) return BigDecimal.ZERO;

        switch (this) {
            case DESPESA:
            case TRANSFERENCIA_SAIDA:
                // Retorna o valor negativo (ex: 100 vira -100; -100 continua -100)
                return valor.abs().negate();
                
            case RECEITA:
            case TRANSFERENCIA_ENTRADA:
            default:
                // Retorna o valor positivo (ex: -100 vira 100; 100 continua 100)
                return valor.abs();
        }
    }
}
