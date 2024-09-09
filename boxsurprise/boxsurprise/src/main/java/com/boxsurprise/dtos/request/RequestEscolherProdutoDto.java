package com.boxsurprise.dtos.request;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestEscolherProdutoDto {

    private Integer idPessoa;
    private Integer idEndereco;
    private Integer idProduto;
    private BigDecimal valor;
    private String tipoProduto;
    private String tamanhoCaixa;
    private String respostasBox;
    private String tema;
    private Integer quantidade;
}

