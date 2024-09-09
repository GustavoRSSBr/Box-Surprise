package com.boxsurprise.enuns;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public enum EnumTamanhoCaixa {
    PEQUENA(new BigDecimal("200.00")),
    MEDIA(new BigDecimal("450.00")),
    GRANDE(new BigDecimal("800.00"));

    private final BigDecimal valor;

    EnumTamanhoCaixa(BigDecimal valor) {
        this.valor = valor;
    }
}
