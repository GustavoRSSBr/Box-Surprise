package com.boxsurprise.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoResponseDto {
    private Integer idProduto;
    private String titulo;
    private String descricao;
    private BigDecimal valor;
    private String tipoProduto;
    private String tamanhoCaixa;
    private String respostasBox;
    private String tema;
}

