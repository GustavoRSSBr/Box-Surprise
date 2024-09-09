package com.boxsurprise.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private Long idProduto;
    private String titulo;
    private String descricao;
    private BigDecimal valor;
    private String tipoProduto;
    private String tamanhoCaixa;
    private String respostasBox;
    private String tema;
}
