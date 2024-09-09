package com.boxsurprise.dtos.request;

import com.boxsurprise.enuns.EnumTamanhoCaixa;
import com.boxsurprise.enuns.EnumTipoProduto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestProdutoDto {
    private String titulo;
    private String descricao;
    private BigDecimal valor;
    private EnumTipoProduto tipoProduto;
    private EnumTamanhoCaixa tamanhoCaixa;

}
